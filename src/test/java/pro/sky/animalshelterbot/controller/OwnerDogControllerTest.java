package pro.sky.animalshelterbot.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerDogController.class)
public class OwnerDogControllerTest {
    @Autowired
    private MockMvc mvc;
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
        final long id = 1L;
        final long chatId = 14447774L;
        final String name = "Bob";
        final String phone = "+79805647855";
        final int age = 18;
        final OwnerStatus status = OwnerStatus.IN_SEARCH;
        final long numberOfReportDays = 14L;
        final Dog dog = new Dog();
        final JSONObject object = new JSONObject();
        final OwnerDog ownerDog = new OwnerDog();
        object.put("id", id);
        object.put("chatId", chatId);
        object.put("name", name);
        object.put("phone", phone);
        object.put("age", age);
        object.put("dog", dog);
        object.put("status", status);
        object.put("numberOfReportDays", numberOfReportDays);

        dog.setId(2L);
        dog.setName("Dog");
        dog.setAge(1);
        dog.setBreed("mops");
        dog.setStatus(PetStatus.FREE);

        ownerDog.setId(id);
        ownerDog.setChatId(chatId);
        ownerDog.setName(name);
        ownerDog.setPhone(phone);
        ownerDog.setStatus(status);
        ownerDog.setAge(age);
        ownerDog.setDog(dog);
        ownerDog.setNumberOfReportDays(numberOfReportDays);

        when(repository.save(ownerDog)).thenReturn(ownerDog);
        when(dogRepository.save(dog)).thenReturn(dog);
        when(service.create(ownerDog,status)).thenReturn(ownerDog);
        when(dogService.createDog(dog, PetStatus.FREE)).thenReturn(dog);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(ownerDog));
        when(repository.existsById(eq(id))).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/owners_dog")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(OwnerStatus.IN_SEARCH)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.dog").value(dog))
                .andExpect(jsonPath("$.numberOfReportDays").value(numberOfReportDays));

        mvc.perform(MockMvcRequestBuilders
                        .get("/owners_dog" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.dog").value(dog))
                .andExpect(jsonPath("$.numberOfReportDays").value(numberOfReportDays));

        mvc.perform(MockMvcRequestBuilders
                        .put("/owners_dog")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.dog").value(dog))
                .andExpect(jsonPath("$.numberOfReportDays").value(numberOfReportDays));

        mvc.perform(MockMvcRequestBuilders
                        .delete("/owners_dog/" + id)
                        .content(object.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/owners_dog")
                        .content(object.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
