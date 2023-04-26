package pro.sky.animalshelterbot.services;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.Commands;

import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.*;
import pro.sky.animalshelterbot.repository.ReportCatRepository;
import pro.sky.animalshelterbot.repository.ReportDogRepository;
import pro.sky.animalshelterbot.service.ReportCatService;
import pro.sky.animalshelterbot.service.ReportDogService;
import pro.sky.animalshelterbot.service.SendReportMenuService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendReportMenuServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ReportDogRepository reportDogRepository;
    @Mock
    private ReportCatRepository catRepository;

    @Mock
    private ReportDogService reportDogService;
    @Mock
    private ReportCatService reportCatService;
    @InjectMocks
    SendReportMenuService sendReportMenuService;
    private static final String TEXT = "ЗАГРУЗИТЕ ОТЧЕТ В ФОРМАТЕ: \n \n" +
            "Рацион: данные о рационе \n" +
            "Информация: общая информация \n" +
            "Привычки: данные о изменении привычек \n" +
            "И прикрепите фото к отчету.";

    private final GetFileResponse getFileResponse = BotUtils.fromJson("{\n" +
                                                                      "\"ok\": true,\n" +
                                                                      "\"file\": {\n" +
                                                                      "    \"file_id\": \"qwerty\"\n" +
                                                                      "    }\n" +
                                                                      "}\n", GetFileResponse.class);
    private final byte[] photo = Files.readAllBytes(
            Paths.get(Objects.requireNonNull(UpdatesListener.class.getResource("/static/img/Cat.jpg")).toURI())
    );

    public SendReportMenuServiceTest() throws IOException, URISyntaxException {
    }

    @Test
    void reportFormTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        SendMessage info = new SendMessage(update.callbackQuery().message().chat().id(),TEXT);

        when(chat.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn(String.valueOf(Commands.SUBMIT_REPORT));
        when(update.callbackQuery().message()).thenReturn(message);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        SendMessage sendMessage = argumentCaptor.getValue();
        Assertions.assertEquals(sendMessage.getParameters().get("chat_id"), 123L);
        Assertions.assertEquals(sendMessage.getParameters().get("text"), info);
    }

    @Test
    void reportTest() throws IOException {
        when(telegramBot.execute(any(GetFile.class))).thenReturn(getFileResponse);
        when(telegramBot.getFileContent(any())).thenReturn(photo);
        ArgumentCaptor<GetFile> argumentCaptor = ArgumentCaptor.forClass(GetFile.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        GetFile actual = argumentCaptor.getValue();
        Assertions.assertEquals(1L, actual.getParameters().get("chat_id"));
        Assertions.assertEquals("Отчет успешно принят!", actual.getParameters().get("text"));
    }

}
