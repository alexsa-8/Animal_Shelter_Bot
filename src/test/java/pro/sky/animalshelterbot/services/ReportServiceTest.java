package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.Report;
import pro.sky.animalshelterbot.exception.ReportNotFoundException;
import pro.sky.animalshelterbot.repository.ReportRepository;
import pro.sky.animalshelterbot.service.ReportService;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository repository;

    @InjectMocks
    private ReportService service;

    private Report report1;
    private Report report2;

    @BeforeEach
    public void setUp(){
        byte[] photo = {3,1};
        report1 = new Report(1L,photo,"Animal diet",
                "Info","change behavior", LocalDate.now());

        report2 = new Report(2L,photo,"other diet",
                "Info","other behavior", LocalDate.now());
    }

    @Test
    public void shouldCreateReport(){
        when(repository.save(report1)).thenReturn(report1);
        assertEquals(report1,service.createReport(report1));
    }

    @Test
    public void shouldDownloadReport(){
        byte[] photo = {3,1};
        when(repository.save(report1)).thenReturn(report1);
        assertEquals(report1,service.downloadReport(1L,
                "Animal diet", "Info",
                "change behavior",photo, LocalDate.now()));
    }

    @Test
    public void shouldFindReport(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(report1));
        assertEquals(report1,service.findReport(1L));
    }

    @Test
    public void shouldGetExceptionsWhenNotFound(){
        when(repository.findById(anyLong())).thenThrow(new ReportNotFoundException());
        assertThrows(ReportNotFoundException.class, () -> service.findReport(anyLong()));
    }

    @Test
    public void shouldUpdateReport(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(report1));

        report1.setId(1L);
        Report expected = report1;
        expected.setReportStatus(ReportStatus.REPORT_ACCEPTED);
        assertEquals(expected,service.updateReport(report1,ReportStatus.REPORT_ACCEPTED));
    }

    @Test
    public void shouldGetExceptionWhenUpdate(){
        report1.setId(null);
        assertThrows(ReportNotFoundException.class,
                () -> service.updateReport(report1,ReportStatus.REPORT_ACCEPTED));
    }

    @Test
    public void shouldDeleteReport(){

    }

    @Test
    public void shouldGetAllReports(){
        Collection<Report> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);

        when(repository.findAll()).thenReturn((List<Report>) reports);
        assertEquals(reports,service.getAllReport());
    }
}
