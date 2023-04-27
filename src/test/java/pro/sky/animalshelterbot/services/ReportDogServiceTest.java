package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.ReportDog;
import pro.sky.animalshelterbot.exception.ReportNotFoundException;
import pro.sky.animalshelterbot.repository.ReportDogRepository;
import pro.sky.animalshelterbot.service.ReportDogService;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportDogServiceTest {

    @Mock
    private ReportDogRepository repository;

    @InjectMocks
    private ReportDogService service;

    private ReportDog reportDog1;
    private ReportDog reportDog2;

    @BeforeEach
    public void setUp(){
        byte[] photo = {3,1};
        reportDog1 = new ReportDog(1L,photo,"Animal diet",
                "Info","change behavior", LocalDate.now());

        reportDog2 = new ReportDog(2L,photo,"other diet",
                "Info","other behavior", LocalDate.now());
    }

    @Test
    public void shouldCreateReport(){
        when(repository.save(reportDog1)).thenReturn(reportDog1);
        assertEquals(reportDog1,service.createReport(reportDog1));
    }

    @Test
    public void shouldDownloadReport(){
        byte[] photo = {3,1};
        when(repository.save(reportDog1)).thenReturn(reportDog1);
        assertEquals(reportDog1,service.downloadReport(1L,
                "Animal diet", "Info",
                "change behavior",photo, LocalDate.now()));
    }

    @Test
    public void shouldFindReport(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(reportDog1));
        assertEquals(reportDog1,service.findReport(1L));
    }

    @Test
    public void shouldGetExceptionsWhenNotFound(){
        when(repository.findById(anyLong())).thenThrow(new ReportNotFoundException());
        assertThrows(ReportNotFoundException.class, () -> service.findReport(anyLong()));
    }

    @Test
    public void shouldUpdateReport(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(reportDog1));

        reportDog1.setId(1L);
        ReportDog expected = reportDog1;
        expected.setReportStatus(ReportStatus.REPORT_ACCEPTED);
        assertEquals(expected,service.updateReport(reportDog1,ReportStatus.REPORT_ACCEPTED));
    }

    @Test
    public void shouldGetExceptionWhenUpdate(){
        reportDog1.setId(null);
        assertThrows(ReportNotFoundException.class,
                () -> service.updateReport(reportDog1,ReportStatus.REPORT_ACCEPTED));
    }

    @Test
    public void shouldDeleteReport(){
        service.deleteReport(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void shouldGetAllReports(){
        Collection<ReportDog> reportDogs = new ArrayList<>();
        reportDogs.add(reportDog1);
        reportDogs.add(reportDog2);

        when(repository.findAll()).thenReturn((List<ReportDog>) reportDogs);
        assertEquals(reportDogs,service.getAllReport());
    }
}
