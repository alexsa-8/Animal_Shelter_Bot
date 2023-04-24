package pro.sky.animalshelterbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.ReportDog;
import pro.sky.animalshelterbot.service.ReportDogService;

import java.util.Collection;

/**
 * Контроллер ReportDogController
 * Контроллер используется для добавления, редактирования, удаления и поиска отчетов в БД через REST-запросы
 * @see ReportDogService
 * @author Gubina Marina
 */
@RestController
@RequestMapping("/reports_dog")
public class ReportDogController {

    private final ReportDogService service;

    public ReportDogController(ReportDogService service){
        this.service = service;
    }

    @Operation(
            summary = "Добавление отчета в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный отчет",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportDog.class)
                    )
            ),
            tags = "Отчеты по собакам"
    )
    @PostMapping
    public ReportDog createReport(@RequestBody ReportDog reportDog){
        return service.createReport(reportDog);
    }

    @Operation(
            summary = "Поиск отчета в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class))
                    )
            },
            tags = "Отчеты по собакам"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReportDog> findReport(@PathVariable Long id){
        ReportDog reportDog = service.findReport(id);
        if(reportDog == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDog);
    }

    @Operation(
            summary = "Редактирование отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class))
                    )
            },
            tags = "Отчеты по собакам"
    )
    @PutMapping
    public ResponseEntity<ReportDog> updateReport(
            @RequestBody ReportDog reportDog,
            @Parameter(description = "Введите статус отчета")
            @RequestParam(name = "Статус")ReportStatus status){
        ReportDog reportDog1 = service.updateReport(reportDog, status);
        if(reportDog1 == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDog1);
    }

    @Operation(
            summary = "Удаление отчета в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class))
                    )
            },
            tags = "Отчеты по собакам"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id){
        service.deleteReport(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение всех отчетов из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все отчеты из БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    )
            },
            tags = "Отчеты по собакам"
    )
    @GetMapping
    public ResponseEntity<Collection<ReportDog>> getAll(){
        return ResponseEntity.ok(service.getAllReport());
    }
}
