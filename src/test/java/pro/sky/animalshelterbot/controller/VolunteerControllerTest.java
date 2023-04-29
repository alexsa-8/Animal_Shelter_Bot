package pro.sky.animalshelterbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pro.sky.animalshelterbot.constant.VolunteerStatus;
import pro.sky.animalshelterbot.entity.Volunteer;
import pro.sky.animalshelterbot.repository.VolunteerRepository;
import pro.sky.animalshelterbot.service.VolunteerService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VolunteerRepository repository;

    @SpyBean
    private VolunteerService service;

    @InjectMocks
    private VolunteerController controller;

    @Test
    public void testCreate() throws Exception {
        final long id = 1L;
        final long chatId = 1L;
        final String name = "Ivan";
        final String phone = "+79001231212";
        final VolunteerStatus status = VolunteerStatus.ON_LINE;
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", id);
        jsonObject.put("chatId", chatId);
        jsonObject.put("name", name);
        jsonObject.put("phone", phone);
        jsonObject.put("status", status);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setChatId(chatId);
        volunteer.setName(name);
        volunteer.setPhone(phone);
        volunteer.setStatus(status);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(2L);
        volunteer2.setChatId(2L);
        volunteer2.setName("Dima");
        volunteer2.setPhone("+79001112233");
        volunteer2.setStatus(VolunteerStatus.ON_LINE);

        when(repository.save(volunteer)).thenReturn(volunteer);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));
        when(repository.findAll()).thenReturn(List.of(volunteer, volunteer2));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(VolunteerStatus.ON_LINE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.status").value(status.toString()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.status").value(status.toString()));

        volunteer.setStatus(VolunteerStatus.ON_LINE);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteer")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(VolunteerStatus.ON_LINE)))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.status").value(VolunteerStatus.ON_LINE.toString()));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(volunteer, volunteer2))));
    }
}