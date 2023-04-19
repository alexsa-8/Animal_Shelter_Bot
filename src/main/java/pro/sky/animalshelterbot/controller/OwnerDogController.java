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
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.service.OwnerDogService;

import java.util.Collection;

/**
 * Контроллер OwnerDogController
 * Контроллер используется для добавления, редактирования, удаления и поиска владельца собаки в БД через REST-запросы
 * @see OwnerDogService
 * @author Kilikova Anna
 */

@RestController
@RequestMapping("owners")
public class OwnerDogController {

    private final OwnerDogService service;

    public OwnerDogController(OwnerDogService service) {
        this.service = service;
    }

    @Operation(
            summary = "Добавление владельца в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный владелец",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OwnerDog.class)
                    )
            ),
            tags = "Владельцы собак"
    )
    @PostMapping
    public OwnerDog create(@RequestBody OwnerDog ownerDog,
                           @Parameter(description = "Установка статуса владельцу", example = "IN_SEARCH")
                           @RequestParam(name = "Статус") OwnerStatus status) {
        return service.create(ownerDog, status);
    }

    @Operation(
            summary = "Поиск владельца в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDog.class))
                    )
            },
            tags = "Владельцы собак"
    )
    @GetMapping("{id}")
    public ResponseEntity<OwnerDog> find(@PathVariable Long id) {
        OwnerDog ownerDog = service.find(id);
        if (ownerDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerDog);
    }

    @Operation(
            summary = "Изменение данных  владельца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDog.class))
                    )
            },
            tags = "Владельцы собак"
    )
    @PutMapping
    public ResponseEntity<OwnerDog> update(@RequestBody OwnerDog ownerDog,
                                           @Parameter(description = "Установка статуса владельца", example = "IN_SEARCH")
                                           @RequestParam(name = "Статус") OwnerStatus status) {
        OwnerDog ownerDog1 = service.update(ownerDog, status);
        if (ownerDog1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerDog1);
    }

    @Operation(
            summary = "Удаление владельца из базы данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный владелец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDog.class))
                    )
            },
            tags = "Владельцы собак"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable long id) {
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
                                    array =@ArraySchema(schema = @Schema(implementation = OwnerDog.class))
                            )
                    )
            },
            tags = "Владельцы собак"
    )
    @GetMapping
    public ResponseEntity<Collection<OwnerDog>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/days")
    public ResponseEntity<OwnerDog> changeDays(@RequestParam Long id,
                                          @RequestParam Long numberDays) {
        OwnerDog ownerDog1 = service.changeNumberOfReportDays(id, numberDays);
        if (ownerDog1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerDog1);
    }
}
