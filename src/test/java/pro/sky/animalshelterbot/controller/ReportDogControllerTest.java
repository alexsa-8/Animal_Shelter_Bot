package pro.sky.animalshelterbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.*;
import pro.sky.animalshelterbot.repository.*;
import pro.sky.animalshelterbot.service.*;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportDogControllerTest.class)
public class ReportDogControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OwnerDogRepository repository;
    @MockBean
    private ReportDogRepository reportDogRepository;
    @MockBean
    private DogRepository repository1;
    @MockBean
    private OwnerDogService service;

    @MockBean
    private ReportDogService reportDogService;

    @MockBean
    private DogService dogService;

    @Test
    public void create() throws Exception {
        OwnerDog ownerDog = new OwnerDog();
        ownerDog.setId(1L);
        ownerDog.setName("Bob");
        ownerDog.setAge(18);
        ownerDog.setPhone("+79805647855");
        ownerDog.setChatId(14447774L);
        ownerDog.setStatus(OwnerStatus.PROBATION);
        ownerDog.setNumberOfReportDays(14L);

        Dog dog = new Dog();
        dog.setId(2L);
        dog.setName("Villy");
        dog.setAge(1);
        dog.setBreed("mops");
        ownerDog.setDog(dog);
        ownerDog.setStatus(OwnerStatus.IN_SEARCH);

        ReportDog reportDog = new ReportDog();
        reportDog.setId(1L);
        reportDog.setChatId(11444L);
        reportDog.setPhoto(new byte[]{1,2,3});
        reportDog.setAnimalDiet("eat");
        reportDog.setChangeBehavior("no");
        reportDog.setGeneralInfo("ok");
        reportDog.setOwnerDog(ownerDog);
        LocalDate dateM = LocalDate.of(2023,4, 30);
        reportDog.setDateMessage(dateM);
        reportDog.setReportStatus(ReportStatus.REPORT_POSTED);


        when(reportDogRepository.save(reportDog)).thenReturn(reportDog);
        when(repository1.save(dog)).thenReturn(dog);
        when(repository.save(ownerDog)).thenReturn(ownerDog);
        when(repository.existsById(eq(1L))).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/reports_dog")
                        .content(reportDog.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders
                        .get("/reports_dog/1")
                        .content(reportDog.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/reports_dog/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders
                        .get("/reports_dog"))
                .andExpect(status().isNotFound());




    }


}
