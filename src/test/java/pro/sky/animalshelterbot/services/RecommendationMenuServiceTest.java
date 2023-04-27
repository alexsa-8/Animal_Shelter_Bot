package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@WebMvcTest
public class RecommendationMenuServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void recommendationsTransportationTest() {
        Update update = mock(Update.class);
        String pathDog = "src/test/resources/recommendations/Recommendations_of_Transportation.pdf";
        File file = new File(pathDog);
        SendDocument document = new SendDocument(update.callbackQuery().message().chat().id(),file);

        when(update.callbackQuery().message().chat().id()).thenReturn(123L);
        when(update.callbackQuery().message().text()).thenReturn("/recommendations_dog");

        ArgumentCaptor<SendDocument> argumentCaptor = ArgumentCaptor.forClass(SendDocument.class);
        SendDocument sendDocument = argumentCaptor.getValue();
        Assertions.assertEquals(sendDocument.getParameters().get("chat_id"), 123L);
        Assertions.assertEquals(sendDocument.getParameters().get("pdf"), document);
    }


}
