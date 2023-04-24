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
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.repository.DogRepository;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;
import pro.sky.animalshelterbot.repository.ReportDogRepository;
import pro.sky.animalshelterbot.service.DogService;
import pro.sky.animalshelterbot.service.OwnerDogService;
import pro.sky.animalshelterbot.service.ReportDogService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DogControllerTests {

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
    public void testDog() throws Exception {

        Long id = 1L;
        String name = "Мухтар";
        Integer age = 1;
        String breed = "Овчарка";
        PetStatus status = PetStatus.FREE;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);
        jsonObject.put("breed", breed);
        jsonObject.put("status", status);

        Dog dog = new Dog(id, name, age, breed, status);


        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(dog));
        when(dogRepository.save(dog)).thenReturn(dog);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dogs")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(PetStatus.FREE)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value(breed))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status.toString()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogs/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value(breed))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status.toString()));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dogs")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(PetStatus.BUSY)))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dogs/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogs")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
