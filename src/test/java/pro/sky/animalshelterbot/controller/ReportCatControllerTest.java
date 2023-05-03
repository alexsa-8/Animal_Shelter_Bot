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
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.entity.ReportCat;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.repository.ReportCatRepository;
import pro.sky.animalshelterbot.service.ReportCatService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportCatController.class)
public class ReportCatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportCatRepository repository;

    @MockBean
    private OwnerCatRepository ownerCatRepository;

    @SpyBean
    private ReportCatService service;

    @InjectMocks
    private ReportCatController controller;

    @Test
    public void test() throws Exception{
        final long id = 1L;
        final String animalDiet = "diet";
        final String generalInfo = "info";
        final String changeBehavior = "behavior";
        final ReportStatus reportStatus = ReportStatus.REPORT_ACCEPTED;
        final Long chat_id = 12345L;
        final LocalDate dateMessage = LocalDate.now();
        final byte[] photo = new byte[]{0};
        final OwnerCat ownerCat = new OwnerCat(1L,"owner","+1234567");

        JSONObject owner = new JSONObject();
        owner.put("chatId",1L);
        owner.put("name","owner");
        owner.put("phone","+1234567");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("animalDiet",animalDiet);
        jsonObject.put("generalInfo",generalInfo);
        jsonObject.put("changeBehavior",changeBehavior);
        jsonObject.put("reportStatus",reportStatus);
        jsonObject.put("chatId",chat_id);
        jsonObject.put("dateMessage",dateMessage);
        jsonObject.put("photo", Arrays.toString(photo));
        jsonObject.put("ownerCat", owner);

        ReportCat report = new ReportCat(chat_id,photo,animalDiet,generalInfo,
                changeBehavior,dateMessage);
        report.setId(id);
        report.setReportStatus(reportStatus);
        report.setOwnerCat(ownerCat);

        when(repository.save(any(ReportCat.class))).thenReturn(report);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(report));
        when(repository.existsById(eq(id))).thenReturn(true);
        when(ownerCatRepository.findByChatId(anyLong())).thenReturn(ownerCat);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reports_cat/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animalDiet").value(animalDiet))
                .andExpect(jsonPath("$.generalInfo").value(generalInfo))
                .andExpect(jsonPath("$.changeBehavior").value(changeBehavior))
                .andExpect(jsonPath("$.reportStatus").value(reportStatus.toString()))
                .andExpect(jsonPath("$.chatId").value(chat_id))
                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
                .andExpect(jsonPath("$.ownerCat").value(ownerCat))
                ;

        report.setReportStatus(ReportStatus.REPORT_REJECTED);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reports_cat/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Статус", String.valueOf(ReportStatus.REPORT_REJECTED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animalDiet").value(animalDiet))
                .andExpect(jsonPath("$.generalInfo").value(generalInfo))
                .andExpect(jsonPath("$.changeBehavior").value(changeBehavior))
                .andExpect(jsonPath("$.reportStatus").value(ReportStatus.REPORT_REJECTED.toString()))
                .andExpect(jsonPath("$.chatId").value(chat_id))
                .andExpect(jsonPath("$.dateMessage").value(dateMessage.toString()))
                .andExpect(jsonPath("$.ownerCat").value(ownerCat))
        ;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reports_cat/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/reports_cat")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
