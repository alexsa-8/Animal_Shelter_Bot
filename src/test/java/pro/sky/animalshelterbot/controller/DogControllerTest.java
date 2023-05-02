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
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.repository.DogRepository;
import pro.sky.animalshelterbot.service.DogService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogController.class)
public class DogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DogRepository repository;

    @SpyBean
    private DogService service;

    @InjectMocks
    private DogController controller;

    @Test
    public void testCreate() throws Exception {
        final long id = 1L;
        final String name = "Vasya";
        final int age = 5;
        final String breed = "haski";
        final PetStatus status = PetStatus.FREE;
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);
        jsonObject.put("breed", breed);
        jsonObject.put("status", status);

        Dog dog1 = new Dog();
        dog1.setId(id);
        dog1.setName(name);
        dog1.setAge(age);
        dog1.setBreed(breed);
        dog1.setStatus(status);

        Dog dog2 = new Dog();
        dog2.setId(2L);
        dog2.setName("Masya");

        when(repository.save(dog1)).thenReturn(dog1);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(dog1));
        when(repository.findAll()).thenReturn(List.of(dog1, dog2));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dogs")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(PetStatus.FREE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.breed").value(breed))
                .andExpect(jsonPath("$.status").value(status.toString()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogs/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.breed").value(breed))
                .andExpect(jsonPath("$.status").value(status.toString()));

        dog1.setStatus(PetStatus.BUSY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dogs")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(PetStatus.BUSY)))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.breed").value(breed))
                .andExpect(jsonPath("$.status").value(PetStatus.BUSY.toString()));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dogs/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogs"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(dog1,dog2))));
    }
}
