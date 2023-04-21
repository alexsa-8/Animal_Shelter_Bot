package pro.sky.animalshelterbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.service.OwnerCatService;

import java.util.Collection;

/**
 * Контроллер OwnerCatController
 * Контроллер используется для добавления, редактирования, удаления и поиска владельца кота в БД через REST-запросы
 * @see OwnerCatService
 * @author Gubina Marina
 */

@RestController
@RequestMapping("owners_cat")
public class OwnerCatController {
    private final OwnerCatService service;

    public OwnerCatController(OwnerCatService service) {
        this.service = service;
    }

    @Operation(
            summary = "Добавление владельца в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный владелец",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OwnerCat.class)
                    )
            ),
            tags = "Владельцы котов"
    )
    @PostMapping
    public OwnerCat create(@RequestBody OwnerCat owner,
                           @Parameter(description = "Установка статуса владельцу", example = "IN_SEARCH")
                           @RequestParam(name = "Статус") OwnerStatus status) {
        return service.create(owner, status);
    }

    @Operation(
            summary = "Поиск владельца в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class))
                    )
            },
            tags = "Владельцы котов"
    )
    @GetMapping("{id}")
    public ResponseEntity<OwnerCat> find(@PathVariable Long id) {
        OwnerCat owner = service.find(id);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owner);
    }

    @Operation(
            summary = "Изменение данных  владельца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class))
                    )
            },
            tags = "Владельцы котов"
    )
    @PutMapping
    public ResponseEntity<OwnerCat> update(@RequestBody OwnerCat ownerCat) {
        OwnerCat ownerCat1 = service.update(ownerCat);
        if (ownerCat1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerCat1);
    }

    @Operation(
            summary = "Удаление владельца из базы данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class))
                    )
            },
            tags = "Владельцы котов"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение списка всех владельцев в приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Полученные владельцы",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array =@ArraySchema(schema = @Schema(implementation = OwnerCat.class))
                            )
                    )
            },
            tags = "Владельцы котов"
    )
    @GetMapping
    public ResponseEntity<Collection<OwnerCat>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "Увеличение дней испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class))
                    )
            },
            tags = "Владельцы котов")

    @PutMapping("/days/{id}")
    public ResponseEntity<OwnerCat> changeDays(@Parameter(description = "Введите id владельца", example = "1")
                                               @PathVariable Long id,
                                               @Parameter(description = "1 - увеличить испытательный срок на 14 дней." +
                                                       "2  - увеличить испытательный срок на 30 дней", example = "1")
                                               @RequestParam Long numberDays) {
        OwnerCat ownerCat = service.changeNumberOfReportDays(id, numberDays);
        if (ownerCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerCat);
    }

    @Operation(
            summary = "Изменение статуса  владельца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class))
                    )
            },
            tags = "Владельцы котов"
    )
    @PutMapping("/status/{id}")
    public ResponseEntity<OwnerCat> updateStatus(@Parameter(description = "Введите id владельца", example = "1")
                                                 @PathVariable Long id,
                                                 @Parameter(description = "Выберете статус владельца", example = "IN_SEARCH")
                                                 @RequestParam (name = "Статус") OwnerStatus status) {
        OwnerCat ownerCat = service.updateStatus(id, status);
        if (ownerCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerCat);
    }

    @Operation(
            summary = "Уведомление владельцу от волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Уведомляемый владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerCat.class))
                    )
            },
            tags = "Владельцы котов")

    @PutMapping("/probation/{id}")
    public ResponseEntity<OwnerCat> noticeToOwners(@Parameter(description = "Введите id владельца", example = "1")
                                               @PathVariable Long id,
                                               @Parameter(description = "1 - отчет плохо заполнен. " +
                                                       "2  - прошел испытальельный срок. 3 - не прошел испытательный срок",
                                                       example = "1")
                                               @RequestParam Long number) {
        OwnerCat ownerCat = service.noticeToOwners(id, number);
        if (ownerCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerCat);
    }
}
