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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.repository.DogRepository;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;
import pro.sky.animalshelterbot.repository.ReportDogRepository;
import pro.sky.animalshelterbot.service.DogService;
import pro.sky.animalshelterbot.service.OwnerDogService;
import pro.sky.animalshelterbot.service.ReportDogService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class OwnerDogControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogRepository dogRepository;
    @MockBean
    private OwnerDogRepository ownerDogRepository;
    @MockBean
    private ReportDogRepository reportDogRepository;

    @SpyBean
    private DogService dogService;
    @SpyBean
    private OwnerDogService ownerDogService;
    @SpyBean
    private ReportDogService reportDogService;


    @InjectMocks
    private DogController dogController;
    @InjectMocks
    private OwnerDogController ownerDogController;
    @InjectMocks
    private ReportDogController reportDogController;

    @Test
    public void testOwnerDog() throws Exception {

        Long chatId = 123L;
        String name = "Владимир";
        String phone = "89132423321";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("chatId", chatId);
        jsonObject.put("name", name);
        jsonObject.put("phone", phone);

        OwnerDog ownerDog = new OwnerDog(chatId, name, phone);

        when(ownerDogRepository.save(any(OwnerDog.class))).thenReturn(ownerDog);
        when(ownerDogRepository.findById(any(Long.class))).thenReturn(Optional.of(ownerDog));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/owners")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(OwnerStatus.APPROVED)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(phone));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(chatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(phone));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/owners")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(OwnerStatus.PROBATION)))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/owners/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}

