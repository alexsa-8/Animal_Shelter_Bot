package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pro.sky.animalshelterbot.controller.CatController;
import pro.sky.animalshelterbot.service.ProcessCallbackQueryService;
import pro.sky.animalshelterbot.service.ShelterInfoMenuService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest
public class ShelterInfoMenuServiceTest {

    private final static String TEST = "Рекомендуем начать процесс знакомства и общения с будущим подопечным заранее. " +
            "После того, как вы сделали выбор, начните навещать животное в приюте, строить с ним доверительные отношения. " +
            "Приносить собаке лакомства, начать выводить её на прогулки, аккуратно гладить. " +
            "Это должно происходить спокойно и ненавязчиво, без какого-либо давления с вашей стороны. " +
            "Когда животное начнёт вас узнавать, вилять хвостом при встрече, и позволит с ним играть, " +
            "можно устроить пару гостевых посещений, приведя собаку в дом. " +
            "Это поможет собаке в дальнейшем более легкому знакомству с незнакомой обстановкой и привыканию к новому дому";

    private final static String TEST_2 = "Что нужно знать перед тем как взять кота из приюта?\n" +
            "✅Узнайте возраст и особенности поведения;\n" +
            "✅Расспросите о прошлом котика: как он попал в приют, какие у него были хозяева;\n" +
            "✅Как он себя обычно ведет;\n" +
            "✅Как реагирует, если оставить одного;\n" +
            "✅Как себя ведет с другими животными, если у вас дома уже живет кто-то;\n" +
            "✅Бывает ли на улице и любит ли сбегать туда.\n";
    @InjectMocks
    ShelterInfoMenuService shelterInfoMenuService;
    @Test
    public void datingRules() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        SendMessage info = new SendMessage(update.callbackQuery().message().chat().id(),TEST);
        SendMessage info2 = new SendMessage(update.callbackQuery().message().chat().id(), TEST_2);
        when(chat.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/info");
        when(update.callbackQuery().message()).thenReturn(message);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        SendMessage sendMessage = argumentCaptor.getValue();
        Assertions.assertEquals(sendMessage.getParameters().get("chat_id"), 123L);
        Assertions.assertEquals(sendMessage.getParameters().get("text"), info);
        Assertions.assertEquals(sendMessage.getParameters().get("text"), info2);
    }
}
