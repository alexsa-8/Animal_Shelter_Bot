package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.Commands;
import pro.sky.animalshelterbot.service.AdvicesMenuService;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AdvicesMenuServiceTest {

    @InjectMocks
    private AdvicesMenuService service;

    private TelegramBot telegramBot;

    @Test
    public void advicesCynologistsTest(){

    }
    @Test
    public void listCynologistsTest() {
    }

    @Test
    public void reasonRefusalTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.REASONS_REFUSAL.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        String path = "src/main/resources/adviсe/Reasons_refusal.pdf";
        File reasonsRefusal = new File(path);
        SendDocument expected= new SendDocument(123L,reasonsRefusal);
        expected.caption("Все причины для отказа сводятся к желанию сделать так, чтобы животное, " +
                "которое уже успело вкусить прелести уличной жизни, не оказалась выброшенным снова. " +
                "Поэтому вряд ли стоит обижаться на работников приюта, " +
                "если они не позволили вам забрать домой понравившегося питомца. \n" +
                "\nС самыми частыми причинами отказов можете ознакомиться в прикрепленном файле \u2191");

        service.reasonsRefusal(update);
        ArgumentCaptor<SendDocument> argumentCaptor = ArgumentCaptor.forClass(SendDocument.class);
        //verify(service).reasonsRefusal(argumentCaptor.capture());
        assertEquals(expected, argumentCaptor.getValue());
    }
}
