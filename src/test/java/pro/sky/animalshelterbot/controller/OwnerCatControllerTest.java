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
import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.repository.CatRepository;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.service.CatService;
import pro.sky.animalshelterbot.service.OwnerCatService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerCatController.class)
public class OwnerCatControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OwnerCatRepository repository;
    @MockBean
    private CatRepository catRepository;
    @MockBean
    private OwnerCatService service;
    @MockBean
    private CatService catService;
    @InjectMocks
    private OwnerCatController controller;
    @InjectMocks
    private CatController catController;

    @Test
    public void ownerCatControllerTest() throws Exception {
        final long id = 1L;
        final long chatId = 14447774L;
        final String name = "Bob";
        final String phone = "+79805647855";
        final int age = 18;
        final OwnerStatus status = OwnerStatus.IN_SEARCH;
        final long numberOfReportDays = 14L;
        final  Cat cat = new Cat();
        final JSONObject object = new JSONObject();
        final OwnerCat ownerCat = new OwnerCat();
        object.put("id", id);
        object.put("chatId", chatId);
        object.put("name", name);
        object.put("phone", phone);
        object.put("age", age);
        object.put("cat", cat);
        object.put("status", status);
        object.put("numberOfReportDays", numberOfReportDays);

        cat.setId(2L);
        cat.setName("Villy");
        cat.setAge(1);
        cat.setBreed("british");
        cat.setStatus(PetStatus.FREE);

        ownerCat.setId(id);
        ownerCat.setChatId(chatId);
        ownerCat.setName(name);
        ownerCat.setPhone(phone);
        ownerCat.setStatus(status);
        ownerCat.setAge(age);
        ownerCat.setCat(cat);
        ownerCat.setNumberOfReportDays(numberOfReportDays);

        when(repository.save(ownerCat)).thenReturn(ownerCat);
        when(catRepository.save(cat)).thenReturn(cat);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(ownerCat));
        when(repository.existsById(eq(id))).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/owners_cat")
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
                .andExpect(jsonPath("$.cat").value(cat))
                .andExpect(jsonPath("$.numberOfReportDays").value(numberOfReportDays));

        mvc.perform(MockMvcRequestBuilders
                        .get("/owners_cat/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.status").value(status.toString()))
                .andExpect(jsonPath("$.numberOfReportDays").value(numberOfReportDays));

        mvc.perform(MockMvcRequestBuilders
                        .put("/owners_cat")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(OwnerStatus.PROBATION)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.status").value(OwnerStatus.PROBATION.toString()))
                .andExpect(jsonPath("$.numberOfReportDays").value(numberOfReportDays));

        mvc.perform(MockMvcRequestBuilders
                        .delete("/owners_cat/" + id)
                        .content(object.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mvc.perform(MockMvcRequestBuilders
                        .get("/owners_cat")
                        .content(object.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(ownerCat))));


    }
}
