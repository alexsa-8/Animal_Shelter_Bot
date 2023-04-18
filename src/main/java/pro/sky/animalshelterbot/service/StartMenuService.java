package pro.sky.animalshelterbot.service;

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
 * Сервис StartMenuService
 * Сервис для стартового меню
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class StartMenuService {

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Метод для запуска меню
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendMessage startMenu(Update update) {
        String message;
        if (ProcessCallbackQueryService.isDog()) {
            message = "Отлично, мы с радостью поможем подобрать тебе щенка, " +
                    "тут ты можешь узнать всю необходимую информацию о приюте и животных, " +
                    "если понадобится помощь, ты всегда можешь позвать волонтера";
        } else {
            message = "Отлично, мы с радостью поможем подобрать тебе котенка, " +
                    "тут ты можешь узнать всю необходимую информацию о приюте и животных, " +
                    "если понадобится помощь, ты всегда можешь позвать волонтера";
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.INFO.getDescription())
                        .callbackData(Commands.INFO.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.ANIMAL_INFO.getDescription())
                        .callbackData(Commands.ANIMAL_INFO.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.SUBMIT_REPORT.getDescription())
                        .callbackData(Commands.SUBMIT_REPORT.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CALL_VOLUNTEER.getDescription())
                        .callbackData(Commands.CALL_VOLUNTEER.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("\uD83D\uDCE2 Расскажи о нас")
                        .switchInlineQuery("Помоги найти новый дом питомцам!")
        );


        SendMessage mes = new SendMessage(update.callbackQuery().message().chat().id(), message);
        mes.replyMarkup(inlineKeyboardMarkup);

        return mes;
    }

    /**
     * Метод, присылающий информацию по связи с волонтером для пользователя
     *
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    public SendMessage volunteerMenu(Update update) {
        SendMessage volunteer = new SendMessage(update.callbackQuery().message().chat().id(), "Волонтер скоро с вами свяжется\uD83D\uDE09");
        return volunteer;
    }


}
