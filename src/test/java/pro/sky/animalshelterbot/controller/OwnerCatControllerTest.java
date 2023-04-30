package pro.sky.animalshelterbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animalshelterbot.constant.OwnerStatus;

import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.repository.CatRepository;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.service.CatService;
import pro.sky.animalshelterbot.service.OwnerCatService;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        OwnerCat ownerCat = new OwnerCat();
        ownerCat.setId(1L);
        ownerCat.setName("Bob");
        ownerCat.setAge(18);
        ownerCat.setPhone("+79805647855");
        ownerCat.setChatId(14447774L);
        ownerCat.setStatus(OwnerStatus.PROBATION);
        ownerCat.setNumberOfReportDays(14L);


        Cat cat = new Cat();
        cat.setId(2L);
        cat.setName("Villy");
        cat.setAge(1);
        cat.setBreed("british");
        ownerCat.setCat(cat);

        OwnerStatus status = OwnerStatus.IN_SEARCH;

        String jsonRequest = new ObjectMapper().writeValueAsString(ownerCat);

        mvc.perform(post("/owners_cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .param("Статус", status.name()))
                .andExpect(status().isOk());

        when(service.find(ownerCat.getId())).thenReturn(ownerCat);

        mvc.perform(get("/owners_cat/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ownerCat.getId()))
                .andExpect(jsonPath("$.chatId").value(ownerCat.getChatId()))
                .andExpect(jsonPath("$.name").value(ownerCat.getName()))
                .andExpect(jsonPath("$.phone").value(ownerCat.getPhone()))
                .andExpect(jsonPath("$.age").value(ownerCat.getAge()))
                .andExpect(jsonPath("$.status").value(ownerCat.getStatus() != null ? ownerCat.getStatus().name() : null))
                .andExpect(jsonPath("$.numberOfReportDays").value(ownerCat.getNumberOfReportDays()))
                .andExpect(jsonPath("$.cat.name").value(cat.getName()))
                .andExpect(jsonPath("$.cat.id").value(cat.getId()))
                .andExpect(jsonPath("$.cat.age").value(cat.getAge()))
                .andExpect(jsonPath("$.cat.breed").value(cat.getBreed()))
                .andExpect(jsonPath("$.cat.status").value(cat.getStatus() != null ? cat.getStatus().name() : null));


        ownerCat.setPhone("+79822445544");
        String requestPayload = objectMapper.writeValueAsString(ownerCat);

        mvc.perform(put("/owners_cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isNotFound());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/owners_cat/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/owners_cat")
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        when(repository.findById(eq(1L))).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.get("/owners_cat/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/owners_cat/{id}", 1))
                .andExpect(status().isOk());

        mvc.perform(put("/owners_cat/days/{id}", 1))
                .andExpect(status().isBadRequest());

        mvc.perform(put("/owners_cat/status/{id}", 1))
                .andExpect(status().isBadRequest());

        mvc.perform(put("/owners_cat/probation/{id}", 1))
                .andExpect(status().isBadRequest());
    }
}
