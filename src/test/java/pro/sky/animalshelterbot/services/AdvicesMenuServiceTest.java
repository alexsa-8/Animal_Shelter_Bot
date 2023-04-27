package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
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

    @Test
    public void menuTest() {

    }

    @Test
    public void reasonRefusalTest() {
        Update update = new Update();
        String path = "src/main/resources/adviсe/Reasons_refusal.pdf";
        File reasonsRefusal = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                reasonsRefusal);
        sendDocument.caption("Все причины для отказа сводятся к желанию сделать так, чтобы животное, " +
                "которое уже успело вкусить прелести уличной жизни, не оказалась выброшенным снова. " +
                "Поэтому вряд ли стоит обижаться на работников приюта, " +
                "если они не позволили вам забрать домой понравившегося питомца. \n" +
                "\nС самыми частыми причинами отказов можете ознакомиться в прикрепленном файле \u2191");

        assertEquals(sendDocument, service.reasonsRefusal(update));
    }
}
