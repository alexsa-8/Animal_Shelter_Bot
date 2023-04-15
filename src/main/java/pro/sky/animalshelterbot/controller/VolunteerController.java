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
import pro.sky.animalshelterbot.constant.VolunteerStatus;
import pro.sky.animalshelterbot.entity.Volunteer;
import pro.sky.animalshelterbot.service.VolunteerService;

import java.util.Collection;

/**
 * Сервис VolunteerController
 * Сервис используется для занесения усыновителя в БД, получения отчёта и обратная связь по нему,
 * предупреждение о задержке отчёта или его не полном заполнении, информация остаётся животное у хозяина или нет
 * @author Rogozin Alexandr
 * @see VolunteerService
 */

@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    private final VolunteerService service;

    public VolunteerController(VolunteerService service) {
        this.service = service;
    }

    @Operation(
            summary = "Добавление волонтёра в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный волонтёр",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            tags = "Волонтёры"
    )
    @PostMapping
    public Volunteer create(@RequestBody Volunteer volunteer,
                            @Parameter(description = "Установка статуса волонтёра", example = "ON_LINE")
                            @RequestParam(name = "Статус") VolunteerStatus status) {
        return service.create(volunteer, status);
    }

    @Operation(
            summary = "Поиск волонтёра в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный волонтёр",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтёр не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Волонтёры"
    )
    @GetMapping("{id}")
    public ResponseEntity<Volunteer> find(@Parameter(description = "Ввод id волонтёра", name = "ID волонтёра")
                                          @PathVariable Long id) {
        Volunteer volunteer = service.find(id);
        if (volunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    @Operation(
            summary = "Изменение данных волонтёра",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный волонтёр",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтёр не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Волонтёры"
    )
    @PutMapping()
    public ResponseEntity<Volunteer> update(@RequestBody Volunteer volunteer,
                                            @Parameter(description = "Установка статуса волонтёра", example = "ON_LINE")
                                            @RequestParam(name = "Статус") VolunteerStatus status) {
        Volunteer volunteer1 = service.create(volunteer, status);
        if (volunteer1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer1);
    }

    @Operation(
            summary = "Удаление волонтёра из базы данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удалённый волонтёр",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтёр не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Волонтёры"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVolunteer(@Parameter(description = "Ввод id волонтёра", name = "ID волонтёра")
                                                @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение списка всех волонтёров в приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Полученные волонтёры",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class)
                                    )
                            )
                    )
            },
            tags = "Волонтёры"
    )
    @GetMapping
    public ResponseEntity<Collection<Volunteer>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
