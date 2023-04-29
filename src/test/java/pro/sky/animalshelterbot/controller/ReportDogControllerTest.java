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
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.entity.ReportDog;
import pro.sky.animalshelterbot.repository.ReportDogRepository;
import pro.sky.animalshelterbot.service.ReportDogService;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportDogController.class)
public class ReportDogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportDogRepository repository;

    @SpyBean
    private ReportDogService service;

    @InjectMocks
    private ReportDogController controller;

    @Test
    public void test() throws Exception{
        final long id = 1L;
        final String animal_diet = "diet";
        final String general_info = "info";
        final String change_behavior = "behavior";
        final ReportStatus status = ReportStatus.REPORT_ACCEPTED;
        final Long chat_id = 12345L;
        final LocalDate date_message = LocalDate.now();
        final byte[] photo = {0};
        final OwnerDog owner_dog_id = new OwnerDog(2L,"owner","12345");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("animal_diet",animal_diet);
        jsonObject.put("general_info",general_info);
        jsonObject.put("change_behavior",change_behavior);
        jsonObject.put("status",status);
        jsonObject.put("chat_id",chat_id);
        jsonObject.put("date_message",date_message);
        jsonObject.put("photo",photo);
        jsonObject.put("owner_dog_id",owner_dog_id.toString());

        ReportDog report = new ReportDog();
        report.setId(id);
        report.setAnimalDiet(animal_diet);
        report.setGeneralInfo(general_info);
        report.setChangeBehavior(change_behavior);
        report.setReportStatus(status);
        report.setChatId(chat_id);
        report.setDateMessage(date_message);
        report.setPhoto(photo);
        report.setOwnerDog(owner_dog_id);

        when(repository.save(report)).thenReturn(report);
        when(repository.findById(1L)).thenReturn(Optional.of(report));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reports_dog/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animal_diet").value(animal_diet))
                .andExpect(jsonPath("$.general_info").value(general_info))
                .andExpect(jsonPath("$.change_behavior").value(change_behavior))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.chat_id").value(chat_id))
                .andExpect(jsonPath("$.date_message").value(date_message))
                .andExpect(jsonPath("$.photo").value(photo))
                .andExpect(jsonPath("$.owner_dog_id").value(owner_dog_id));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reports_dog")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animal_diet").value(animal_diet))
                .andExpect(jsonPath("$.general_info").value(general_info))
                .andExpect(jsonPath("$.change_behavior").value(change_behavior))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.chat_id").value(chat_id))
                .andExpect(jsonPath("$.date_message").value(date_message))
                .andExpect(jsonPath("$.photo").value(photo))
                .andExpect(jsonPath("$.owner_dog_id").value(owner_dog_id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reports_dog/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reports_dog")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animal_diet").value(animal_diet))
                .andExpect(jsonPath("$.general_info").value(general_info))
                .andExpect(jsonPath("$.change_behavior").value(change_behavior))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.chat_id").value(chat_id))
                .andExpect(jsonPath("$.date_message").value(date_message))
                .andExpect(jsonPath("$.photo").value(photo))
                .andExpect(jsonPath("$.owner_dog_id").value(owner_dog_id));
    }
}
