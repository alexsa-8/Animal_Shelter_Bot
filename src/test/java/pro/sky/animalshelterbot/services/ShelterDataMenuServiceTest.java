package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.Commands;
import pro.sky.animalshelterbot.entity.User;
import pro.sky.animalshelterbot.repository.UserRepository;
import pro.sky.animalshelterbot.service.ShelterDataMenuService;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShelterDataMenuServiceTest {

    @InjectMocks
    private ShelterDataMenuService service;

    @Mock
    private UserRepository repository;

    @Mock
    private TelegramBot telegramBot;

    @Test
    public void shelterRecommendationIsDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.SHELTER_RECOMMENDATIONS.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",true);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String pathDog = "src/main/resources/shelterInfo/Safety_in_shelter.pdf";
        File recommendation = new File(pathDog);
        SendDocument expected = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        expected.caption("Рекомендации по технике безопасности на территории " +
                "приюта в прикрепленном документе \u2191 ");

        service.shelterRecommendation(update);
        ArgumentCaptor<SendDocument> argumentCaptor = ArgumentCaptor.forClass(SendDocument.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendDocument actual = argumentCaptor.getValue();

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void shelterRecommendationIsNotDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.SHELTER_RECOMMENDATIONS.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",false);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String informationCatShelter = "✅ Работники и посетители приюта обязаны соблюдать правила личной гигиены, " +
                "в том числе мыть руки с дезинфицирующими средствами после общения с животными.\n" +
                "❌ Нахождение на территории в излишне возбужденном состоянии, а также в состоянии алкогольного, " +
                "наркотического или медикаментозного опьянения строго запрещено.";

        SendMessage expected = new SendMessage(update.callbackQuery().message().chat().id(),informationCatShelter);
        service.shelterRecommendation(update);
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void shelterDataIsDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.SHELTER_DATA.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",true);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String dataMessageDogShelter = "  Доброго времени суток! Наши контактные данные:" +
                "\n Адрес: г. Астана, Сарыарка район, Коктал ж/м, ул. Аккорган, 5в. " +
                " \n Часы работы приюта: ежедневно с 11:00 до 18:00 \n Тел.: +7‒702‒481‒01‒58" +
                " \n Email: animalshelterastaba@gmail.com  \n";
        String pathDog = "src/main/resources/shelterInfo/map.jpg";
        File map = new File(pathDog);
        SendPhoto expected = new SendPhoto(update.callbackQuery().message().chat().id(), map);
        expected.caption(dataMessageDogShelter + " Схема проезда до нашего приюта \u2191");

        SendPhoto actual = service.shelterData(update);

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void shelterDataIsNotDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.SHELTER_DATA.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",false);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String dataMessageKittenShelter = "  Доброго времени суток! Наши контактные данные:" +
                "\n Адрес: г. Алматы, ул. Спортивная дом 3 " +
                " \n Часы работы приюта: ежедневно с 10:00 до 18:00 \n Тел.: +7‒702‒262‒39‒82" +
                " \n Сайт kotopesoff.kz  \n"+
                " \n Email: wotdpress@kotopesoff.kz  \n";
        String pathKitten = "src/main/resources/shelterInfo/mapCatShelter.jpg";
        File map = new File(pathKitten);
        SendPhoto expected = new SendPhoto(update.callbackQuery().message().chat().id(), map);
        expected.caption(dataMessageKittenShelter + " Схема проезда до нашего приюта \u2191");

        SendPhoto actual = service.shelterData(update);

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void carPassIsDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.CAR_PASS.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",true);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String pass = "+7‒702‒481‒01‒58";
        String registrationPass = "Позвоните по этому номеру телефона и оформите пропуск на машину: " + pass;
        SendMessage expected = new SendMessage(update.callbackQuery().message().chat().id(),registrationPass);

        SendMessage actual = service.carPass(update);

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void carPassIsNotDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.CAR_PASS.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",false);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String pass = "+7‒702‒262‒39‒82";
        String registrationPass = "Позвоните по этому номеру телефона и оформите пропуск на машину: " + pass;
        SendMessage expected = new SendMessage(update.callbackQuery().message().chat().id(),registrationPass);

        SendMessage actual = service.carPass(update);

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void choosingPetIsDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.CHOOSING_A_PET.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",true);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String pet = "\uD83D\uDC36 Для выбора собаки в приюте, нажмите на эту ссылку: https://kotopesoff.kz/pets/dogs";
        SendMessage expected = new SendMessage(update.callbackQuery().message().chat().id(),pet);

        SendMessage actual = service.choosingAPet(update);

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }

    @Test
    public void choosingPetIsNotDogTest() throws JSONException {
        JSONObject callbackQuery = new JSONObject();
        String callbackQueryData = Commands.CHOOSING_A_PET.getCallbackData();
        callbackQuery.put("data",callbackQueryData);
        JSONObject chat = new JSONObject();
        chat.put("id",123L);
        JSONObject  message = new JSONObject();
        message.put("chat", chat);
        callbackQuery.put("message",message);
        JSONObject object = new JSONObject();
        object.put("callback_query",callbackQuery);

        Update update = BotUtils.fromJson(object.toString(),Update.class);

        User user = new User(123L,"Name",false);
        when(repository.findUserByChatId(update.callbackQuery().message().chat().id())).thenReturn(user);

        String pet = "\uD83D\uDC31 Для выбора кошки в приюте, нажмите на эту ссылку: https://kotopesoff.kz/pets/cats";
        SendMessage expected = new SendMessage(update.callbackQuery().message().chat().id(),pet);

        SendMessage actual = service.choosingAPet(update);

        assertEquals(expected.getClass(),actual.getClass());
        assertEquals(expected.getParameters(),actual.getParameters());
        assertEquals(expected.getFileName(),actual.getFileName());
    }
}
