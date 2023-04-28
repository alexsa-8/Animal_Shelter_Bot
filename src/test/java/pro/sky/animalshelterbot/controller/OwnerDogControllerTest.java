package pro.sky.animalshelterbot.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerCat;
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
    @SpyBean
    private OwnerDogService service;
    @SpyBean
    private DogService dogService;

    @InjectMocks
    private OwnerDogController controller;
    @InjectMocks
    private DogController dogController;


    @Test
    public void ownerCatControllerTest() throws Exception {
        final Long id = 1L;
        final Long chatId = 14447774L;
        final String name = "Bob";
        final String phone = "+79805647855";

        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("chatId", chatId);
        object.put("name", name);
        object.put("phone", phone);

        OwnerDog ownerDog = new OwnerDog(chatId, name, phone);
        when(repository.save(any(OwnerDog.class))).thenReturn(ownerDog);
        when(repository.findById(eq(id))).thenReturn(Optional.of(ownerDog));
        when(repository.existsById(eq(id))).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/owners_dog")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(OwnerStatus.APPROVED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone));

        mvc.perform(MockMvcRequestBuilders
                        .get("/owners_dog" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone));

        mvc.perform(MockMvcRequestBuilders
                        .put("/owners_dog")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone));

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
