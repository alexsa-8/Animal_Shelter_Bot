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
import pro.sky.animalshelterbot.entity.Report;
import pro.sky.animalshelterbot.service.ReportService;

import java.util.Collection;

/**
 * Контроллер ReportController
 * Контроллер используется для добавления, редактирования, удаления и поиска отчетов в БД через REST-запросы
 * @see ReportService
 * @author Gubina Marina
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service){
        this.service = service;
    }

    @Operation(
            summary = "Добавление отчета в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный отчет",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Report.class)
                    )
            ),
            tags = "Отчеты"
    )
    @PostMapping
    public Report createReport(@RequestBody Report report){
        return service.createReport(report);
    }

    @Operation(
            summary = "Поиск отчета в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))
                    )
            },
            tags = "Отчеты"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Report> findReport(@PathVariable Long id){
        Report report = service.findReport(id);
        if(report == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }

    @Operation(
            summary = "Редактирование отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))
                    )
            },
            tags = "Отчеты"
    )
    @PutMapping
    public ResponseEntity<Report> updateReport(
            @RequestBody Report report,
            @Parameter(description = "Введите статус отчета")
            @RequestParam(name = "Статус")ReportStatus status){
        Report report1 = service.updateReport(report, status);
        if(report1 == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report1);
    }

    @Operation(
            summary = "Удаление отчета в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))
                    )
            },
            tags = "Отчеты"
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
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Отчеты"
    )
    @GetMapping
    public ResponseEntity<Collection<Report>> getAll(){
        return ResponseEntity.ok(service.getAllReport());
    }
}
