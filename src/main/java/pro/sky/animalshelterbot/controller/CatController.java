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
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.service.CatService;

import java.util.Collection;

/**
 * Контроллер CatController
 * Контроллер используется для добавления, редактирования, удаления и поиска котов в БД через REST-запросы
 * @see CatService
 * @author Gubina Marina
 */

@RestController
@RequestMapping("cats")
public class CatController {
    private final CatService service;

    public CatController(CatService service) {
        this.service = service;
    }

    @Operation(
            summary = "Добавление кота в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный кот",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            tags = "Коты"
    )
    @PostMapping
    public Cat createCat(@RequestBody Cat cat,
                         @Parameter(description = "Установка статуса кота", example = "FREE")
                         @RequestParam(name = "Статус") PetStatus status) {
        return service.createCat(cat, status);
    }

    @Operation(
            summary = "Поиск кота в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный кот",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Кот не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class))
                    )
            },
            tags = "Коты"
    )
    @GetMapping("{id}")
    public ResponseEntity<Cat> findCat(@PathVariable Long id) {
        Cat cat = service.findCat(id);
        if (cat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cat);
    }

    @Operation(
            summary = "Изменение данных кота",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный кот",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Кот не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class))
                    )
            },
            tags = "Коты"
    )
    @PutMapping
    public ResponseEntity<Cat> updateCat(@RequestBody Cat cat,
                                       @Parameter(description = "Установка статуса кота", example = "FREE")
                                         @RequestParam(name = "Статус") PetStatus status) {
        Cat cat1 = service.updateCat(cat, status);
        if (cat1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cat1);
    }

    @Operation(
            summary = "Удаление кота из базы данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный кот",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Кот не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class))
                    )
            },
            tags = "Коты"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable long id) {
        service.deleteCat(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение списка всех котов в приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Полученные коты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array =@ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    )
            },
            tags = "Коты"
    )
    @GetMapping
    public ResponseEntity<Collection<Cat>> getAll() {
        return ResponseEntity.ok(service.getAllCat());
    }

}
