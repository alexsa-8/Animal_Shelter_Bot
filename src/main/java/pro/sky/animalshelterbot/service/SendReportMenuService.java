package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;



/**
 * Сервис SendReportMenuService
 * Сервис меню отправки отчета
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class SendReportMenuService {

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;


    public SendReportMenuService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод, вызывающий подменю по отчетам
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendMessage submitReportMenu(Update update) {
        SendMessage report = new SendMessage(update.callbackQuery().message().chat().id(), "Затычка");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.REPORT_FORM.getDescription())
                        .callbackData(Commands.REPORT_FORM.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CALL_VOLUNTEER.getDescription())
                        .callbackData(Commands.CALL_VOLUNTEER.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK.getDescription())
                        .callbackData(Commands.BACK.getCallbackData())
        );

        report.replyMarkup(inlineKeyboardMarkup);

        return report;
    }

    /**
     * Метод, присылающий форму отчета для пользователя
     *
     * @param update доступное обновление
     * @return форма отчета
     */
    public SendMessage reportForm(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "ЗАГРУЗИТЕ ОТЧЕТ В ФОРМАТЕ \n" +
                        "Рацион: данные о рационе \n"+
                        "Информация: общая информация \n"+
                        "Привычки: данные о изменении привычек \n"+
                        "И прикрепите фото");
        return message;
    }
}
