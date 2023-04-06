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
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.service.DogService;

import java.util.Collection;

/**
 * Контроллер DogController
 * Контроллер используется для добавления, редактирования, удаления и поиска собак в БД через REST-запросы
 * @see DogService
 * @author Kilikova Anna
 */

@RestController
@RequestMapping("dogs")
public class DogController {

    private final DogService service;

    public DogController(DogService service) {
        this.service = service;
    }

    @Operation(
            summary = "Добавление собаки в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленная собака",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            tags = "Собаки"
    )
    @PostMapping
    public Dog createDog(@RequestBody Dog dog,
                         @Parameter(description = "Установка статуса собаки", example = "FREE")
                         @RequestParam(name = "Статус") PetStatus status) {
        return service.createDog(dog, status);
    }

    @Operation(
            summary = "Поиск собаки в базе данных по id",
            responses = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Найденная собака",
                           content = @Content(
                                   mediaType = MediaType.APPLICATION_JSON_VALUE,
                                   schema = @Schema(implementation = Dog.class)
                           )
                   ),
                    @ApiResponse(
                    responseCode = "404",
                    description = "Собака не найдена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class))
                    )
            },
            tags = "Собаки"
    )
    @GetMapping("{id}")
    public ResponseEntity<Dog> findDog(@Parameter(description = "Ввод id собаки", name = "ID собаки") @PathVariable Long id) {
        Dog dog = service.findDog(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @Operation(
            summary = "Изменение данных  собаки",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененная собака",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Собака не найдена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class))
                    )
            },
            tags = "Собаки"
    )
    @PutMapping
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dog,
                                         @Parameter(description = "Установка статуса собаки", example = "FREE")
                                         @RequestParam(name = "Статус") PetStatus status) {
        Dog dog1 = service.updateDog(dog, status);
        if (dog1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog1);
    }

    @Operation(
            summary = "Удаление собаки из базы данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленная собака",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Собака не найдена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class))
                    )
            },
            tags = "Собаки"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDog(@Parameter(description = "Ввод id собаки", name = "ID собаки") @PathVariable long id) {
        service.deleteDog(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение списка всех собак в приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Полученные собаки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array =@ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    )
            },
            tags = "Собаки"
    )
    @GetMapping
    public ResponseEntity<Collection<Dog>> getAll() {
        return ResponseEntity.ok(service.getAllDog());
    }

}
