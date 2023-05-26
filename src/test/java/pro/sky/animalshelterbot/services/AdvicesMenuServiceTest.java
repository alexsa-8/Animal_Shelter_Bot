package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void advicesCynologistsTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.ADVICES_CYNOLOGISTS.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        String path = "src/main/resources/adviсe/Cynologist_adviсes.pdf";
        File advices = new File(path);
        SendDocument expected = new SendDocument(update.callbackQuery().message().chat().id(),
                advices);
        expected.caption("В этом докуметне, мы собрали ответы на самые важные вопросы по воспитанию " +
                "и дресеровке вашего питомца \u2191 ");

        SendDocument actual = service.advicesCynologists(update);
        assertEquals(expected.getFileName(), actual.getFileName());
        assertEquals(expected.getParameters(), actual.getParameters());
    }
    @Test
    public void listCynologistsTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.LIST_CYNOLOGISTS.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        String path = "src/main/resources/adviсe/List_of_cynologists.pdf";
        File listCynologists = new File(path);
        SendDocument expected = new SendDocument(update.callbackQuery().message().chat().id(),
                listCynologists);
        expected.caption("Мы подобрали небольшой список кинологов  \u2191, которые заслужили наше доверие, вы можете смело " +
                "обращаться к ним за помощью и быть уверенны, что ваш питомец в надежных руках!");

        SendDocument actual = service.listCynologists(update);
        assertEquals(expected.getFileName(), actual.getFileName());
        assertEquals(expected.getParameters(), actual.getParameters());
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

        SendDocument actual = service.reasonsRefusal(update);
        assertEquals(expected.getFileName(), actual.getFileName());
        assertEquals(expected.getParameters(), actual.getParameters());
    }
}
