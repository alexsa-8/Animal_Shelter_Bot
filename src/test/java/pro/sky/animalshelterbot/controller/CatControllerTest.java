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
import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.repository.CatRepository;
import pro.sky.animalshelterbot.service.CatService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatController.class)
public class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CatRepository repository;

    @SpyBean
    private CatService service;

    @InjectMocks
    private CatController controller;

    @Test
    public void testCreate() throws Exception {
        final long id = 1L;
        final String name = "Vasya";
        final int age = 5;
        final String breed = "british";
        final PetStatus status = PetStatus.FREE;
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);
        jsonObject.put("breed", breed);
        jsonObject.put("status", status);

        Cat cat = new Cat();
        cat.setId(id);
        cat.setName(name);
        cat.setAge(age);
        cat.setBreed(breed);
        cat.setStatus(status);

        Cat cat2 = new Cat();
        cat2.setId(2L);
        cat2.setName("Masya");

        when(repository.save(cat)).thenReturn(cat);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(cat));
        when(repository.findAll()).thenReturn(List.of(cat, cat2));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cats")
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
                        .get("/cats/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.breed").value(breed))
                .andExpect(jsonPath("$.status").value(status.toString()));

        cat.setStatus(PetStatus.BUSY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cats")
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
                        .delete("/cats/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/cats"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(cat,cat2))));
    }

}
