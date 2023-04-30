package pro.sky.animalshelterbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.repository.DogRepository;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;
import pro.sky.animalshelterbot.service.DogService;
import pro.sky.animalshelterbot.service.OwnerDogService;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerDogController.class)
public class OwnerDogControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OwnerDogRepository repository;
    @MockBean
    private DogRepository dogRepository;
    @MockBean
    private OwnerDogService service;
    @MockBean
    private DogService dogService;
    @InjectMocks
    private OwnerDogController controller;
    @InjectMocks
    private DogController dogController;

    @Test
    public void ownerDogControllerTest() throws Exception {
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
        dog.setName("Dog");
        dog.setAge(1);
        dog.setBreed("mops");
        ownerDog.setDog(dog);

        OwnerStatus status = OwnerStatus.IN_SEARCH;

        String jsonRequest = new ObjectMapper().writeValueAsString(ownerDog);

        mvc.perform(post("/owners_dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .param("Статус", status.name()))
                .andExpect(status().isOk());

        when(service.find(ownerDog.getId())).thenReturn(ownerDog);

        mvc.perform(get("/owners_dog/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ownerDog.getId()))
                .andExpect(jsonPath("$.chatId").value(ownerDog.getChatId()))
                .andExpect(jsonPath("$.name").value(ownerDog.getName()))
                .andExpect(jsonPath("$.phone").value(ownerDog.getPhone()))
                .andExpect(jsonPath("$.age").value(ownerDog.getAge()))
                .andExpect(jsonPath("$.status").value(ownerDog.getStatus() != null ? ownerDog.getStatus().name() : null))
                .andExpect(jsonPath("$.numberOfReportDays").value(ownerDog.getNumberOfReportDays()))
                .andExpect(jsonPath("$.dog.name").value(dog.getName()))
                .andExpect(jsonPath("$.dog.id").value(dog.getId()))
                .andExpect(jsonPath("$.dog.age").value(dog.getAge()))
                .andExpect(jsonPath("$.dog.breed").value(dog.getBreed()))
                .andExpect(jsonPath("$.dog.status").value(dog.getStatus() != null ? dog.getStatus().name() : null));


        ownerDog.setPhone("+79822445544");
        String requestPayload = objectMapper.writeValueAsString(ownerDog);

        mvc.perform(put("/owners_dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/owners_dog/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/owners_dog")
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        when(repository.findById(eq(1L))).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.get("/owners_dog/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/owners_dog/{id}", 1))
                .andExpect(status().isOk());

        mvc.perform(put("/owners_dog/days/{id}", 1))
                .andExpect(status().isBadRequest());

        mvc.perform(put("/owners_dog/status/{id}", 1))
                .andExpect(status().isBadRequest());

        mvc.perform(put("/owners_dog/probation/{id}", 1))
                .andExpect(status().isBadRequest());
    }
}
