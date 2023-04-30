package pro.sky.animalshelterbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.entity.ReportCat;
import pro.sky.animalshelterbot.repository.CatRepository;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.repository.ReportCatRepository;
import pro.sky.animalshelterbot.service.CatService;
import pro.sky.animalshelterbot.service.OwnerCatService;
import pro.sky.animalshelterbot.service.ReportCatService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportCatController.class)
public class ReportCatControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OwnerCatRepository repository;
    @MockBean
    private ReportCatRepository reportCatRepository;
    @MockBean
    private CatRepository catRepository;
    @MockBean
    private OwnerCatService service;

    @MockBean
    private ReportCatService reportCatService;

    @MockBean
    private CatService catService;

    @Test
    public void create() throws Exception {
        OwnerCat ownerCat = new OwnerCat();
        ownerCat.setId(1L);
        ownerCat.setName("Bob");
        ownerCat.setAge(18);
        ownerCat.setPhone("+79805647855");
        ownerCat.setChatId(14447774L);
        ownerCat.setStatus(OwnerStatus.PROBATION);
        ownerCat.setNumberOfReportDays(14L);

        Cat cat = new Cat();
        cat.setId(2L);
        cat.setName("Villy");
        cat.setAge(1);
        cat.setBreed("british");
        ownerCat.setCat(cat);
        ownerCat.setStatus(OwnerStatus.IN_SEARCH);

        ReportCat reportCat = new ReportCat();
        reportCat.setId(1L);
        reportCat.setChatId(11444L);
        reportCat.setPhoto(new byte[]{1,2,3});
        reportCat.setAnimalDiet("eat");
        reportCat.setChangeBehavior("no");
        reportCat.setGeneralInfo("ok");
        reportCat.setOwnerCat(ownerCat);
        LocalDate dateM = LocalDate.of(2023,4, 30);
        reportCat.setDateMessage(dateM);
        reportCat.setReportStatus(ReportStatus.REPORT_POSTED);


        when(reportCatRepository.save(reportCat)).thenReturn(reportCat);
        when(catRepository.save(cat)).thenReturn(cat);
        when(repository.save(ownerCat)).thenReturn(ownerCat);
        when(repository.existsById(eq(1L))).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/reports_cat")
                        .content(reportCat.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .get("/reports_cat/1")
                        .content(reportCat.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders
                .delete("/reports_cat/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/reports_cat"))
                .andExpect(status().isOk());




    }


}
