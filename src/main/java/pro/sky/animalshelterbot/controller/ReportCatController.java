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
import pro.sky.animalshelterbot.entity.ReportCat;
import pro.sky.animalshelterbot.service.ReportCatService;

import java.util.Collection;

/**
 * Контроллер ReportCatController
 * Контроллер используется для добавления, редактирования, удаления
 * и поиска отчетов по котам в БД через REST-запросы
 * @see ReportCatService
 * @author Gubina Marina
 */
@RestController
@RequestMapping("/reports_cat")
public class ReportCatController {

    private final ReportCatService service;

    public ReportCatController(ReportCatService service){
        this.service = service;
    }

    @Operation(
            summary = "Добавление отчета в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленный отчет",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportCat.class)
                    )
            ),
            tags = "Отчеты по котам"
    )
    @PostMapping
    public ReportCat createReport(@RequestBody ReportCat reportCat){
        return service.createReport(reportCat);
    }

    @Operation(
            summary = "Поиск отчета в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class))
                    )
            },
            tags = "Отчеты по котам"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReportCat> findReport(@PathVariable Long id){
        ReportCat reportCat = service.findReport(id);
        if(reportCat == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportCat);
    }

    @Operation(
            summary = "Редактирование отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class))
                    )
            },
            tags = "Отчеты по котам"
    )
    @PutMapping
    public ResponseEntity<ReportCat> updateReport(
            @RequestBody ReportCat reportCat,
            @Parameter(description = "Введите статус отчета")
            @RequestParam(name = "Статус")ReportStatus status){
        ReportCat reportCat1 = service.updateReport(reportCat, status);
        if(reportCat1 == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportCat1);
    }

    @Operation(
            summary = "Удаление отчета в базе данных по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class))
                    )
            },
            tags = "Отчеты по котам"
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
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    )
            },
            tags = "Отчеты по котам"
    )
    @GetMapping
    public ResponseEntity<Collection<ReportCat>> getAll(){
        return ResponseEntity.ok(service.getAllReport());
    }
}
