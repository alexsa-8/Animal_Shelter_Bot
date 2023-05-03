package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.entity.ReportCat;
import pro.sky.animalshelterbot.exception.ReportNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.repository.ReportCatRepository;
import pro.sky.animalshelterbot.service.ReportCatService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportCatServiceTest {
    @Mock
    private ReportCatRepository repository;

    @Mock
    private OwnerCatRepository ownerCatRepository;

    @InjectMocks
    private ReportCatService service;

    private ReportCat reportCat;

    private ReportCat reportCat1;

    private OwnerCat owner;

    @BeforeEach
    public void setUp(){
        byte[] photo = {3,1};
        reportCat = new ReportCat(1L,photo,"Animal diet",
                "Info","change behavior", LocalDate.now());

        reportCat1 = new ReportCat(2L,photo,"other diet",
                "Info","other behavior", LocalDate.now());

        owner = new OwnerCat(1L,"Owner","+1334567");

        reportCat.setOwnerCat(owner);
    }

    @Test
    public void shouldCreateReport(){
        when(repository.save(reportCat)).thenReturn(reportCat);
        assertEquals(reportCat,service.createReport(reportCat));
    }

    @Test
    public void shouldDownloadReport(){
        byte[] photo = {3,1};
        when(repository.save(reportCat)).thenReturn(reportCat);
        when(ownerCatRepository.findByChatId(anyLong())).thenReturn(owner);
        assertEquals(reportCat,service.downloadReport(1L,
                "Animal diet", "Info",
                "change behavior",photo, LocalDate.now()));
    }

    @Test
    public void shouldFindReport(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(reportCat));
        assertEquals(reportCat,service.findReport(1L));
    }

    @Test
    public void shouldGetExceptionsWhenNotFound(){
        when(repository.findById(anyLong())).thenThrow(new ReportNotFoundException());
        assertThrows(ReportNotFoundException.class, () -> service.findReport(anyLong()));
    }

    @Test
    public void shouldUpdateReport(){
        ReportCat expected = reportCat;
        expected.setReportStatus(ReportStatus.REPORT_ACCEPTED);

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(reportCat));
        when(repository.save(reportCat)).thenReturn(reportCat);

        assertEquals(expected,service.updateReport(1L,ReportStatus.REPORT_ACCEPTED));
    }

    @Test
    public void shouldGetExceptionWhenUpdate(){
        reportCat.setId(null);
        assertThrows(ReportNotFoundException.class,
                () -> service.updateReport(null,ReportStatus.REPORT_ACCEPTED));
    }

    @Test
    public void shouldDeleteReport(){
        service.deleteReport(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void shouldGetAllReports(){
        Collection<ReportCat> reportCats = new ArrayList<>();
        reportCats.add(reportCat);
        reportCats.add(reportCat1);

        when(repository.findAll()).thenReturn((List<ReportCat>) reportCats);
        assertEquals(reportCats,service.getAllReport());
    }
}
