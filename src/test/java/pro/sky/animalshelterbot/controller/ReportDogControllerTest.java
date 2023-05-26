package pro.sky.animalshelterbot.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.*;
import pro.sky.animalshelterbot.repository.*;
import pro.sky.animalshelterbot.service.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportDogController.class)
public class ReportDogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportDogRepository repository;

    @MockBean
    private OwnerDogRepository ownerDogRepository;

    @SpyBean
    private ReportDogService service;

    @InjectMocks
    private ReportDogController controller;

    @Test
    public void test() throws Exception{
        final long id = 1L;
        final String animalDiet = "diet";
        final String generalInfo = "info";
        final String changeBehavior = "behavior";
        final ReportStatus reportStatus = ReportStatus.REPORT_ACCEPTED;
        final Long chat_id = 12345L;
        final LocalDate dateMessage = LocalDate.now();
        final byte[] photo = new byte[]{0};
        final OwnerDog ownerDog = new OwnerDog(1L,"owner","+1234567");

        JSONObject owner = new JSONObject();
        owner.put("chatId",1L);
        owner.put("name","owner");
        owner.put("phone","+1234567");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("animalDiet",animalDiet);
        jsonObject.put("generalInfo",generalInfo);
        jsonObject.put("changeBehavior",changeBehavior);
        jsonObject.put("reportStatus",reportStatus);
        jsonObject.put("chatId",chat_id);
        jsonObject.put("dateMessage",dateMessage);
        jsonObject.put("photo", Arrays.toString(photo));
        jsonObject.put("ownerCat", owner);

        ReportDog report = new ReportDog(chat_id,photo,animalDiet,generalInfo,
                changeBehavior,dateMessage);
        report.setId(id);
        report.setReportStatus(reportStatus);
        report.setOwnerDog(ownerDog);

        when(repository.save(any(ReportDog.class))).thenReturn(report);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(report));
        when(repository.existsById(eq(id))).thenReturn(true);
        when(ownerDogRepository.findByChatId(anyLong())).thenReturn(ownerDog);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reports_dog/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animalDiet").value(animalDiet))
                .andExpect(jsonPath("$.generalInfo").value(generalInfo))
                .andExpect(jsonPath("$.changeBehavior").value(changeBehavior))
                .andExpect(jsonPath("$.reportStatus").value(reportStatus.toString()))
                .andExpect(jsonPath("$.chatId").value(chat_id))
                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
                .andExpect(jsonPath("$.ownerDog").value(ownerDog))
        ;

        report.setReportStatus(ReportStatus.REPORT_REJECTED);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reports_dog/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(ReportStatus.REPORT_REJECTED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animalDiet").value(animalDiet))
                .andExpect(jsonPath("$.generalInfo").value(generalInfo))
                .andExpect(jsonPath("$.changeBehavior").value(changeBehavior))
                .andExpect(jsonPath("$.reportStatus").value(ReportStatus.REPORT_REJECTED.toString()))
                .andExpect(jsonPath("$.chatId").value(chat_id))
                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
                .andExpect(jsonPath("$.ownerDog").value(ownerDog))
        ;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reports_dog/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reports_dog")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

