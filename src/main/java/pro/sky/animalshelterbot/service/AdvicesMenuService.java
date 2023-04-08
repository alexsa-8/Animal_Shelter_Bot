package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;

import java.io.File;

/**
 * Сервис AdvicesMenuService
 * Сервис для меню советов
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class AdvicesMenuService {

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
    public SendMessage advicesMenu(Update update) {
        String message = "Список советов";
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.ADVICES_CYNOLOGISTS.getDescription())
                        .callbackData(Commands.ADVICES_CYNOLOGISTS.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.LIST_CYNOLOGISTS.getDescription())
                        .callbackData(Commands.LIST_CYNOLOGISTS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.REASONS_REFUSAL.getDescription())
                        .callbackData(Commands.REASONS_REFUSAL.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK_TO_ANIMAL_MENU.getDescription())
                        .callbackData(Commands.BACK_TO_ANIMAL_MENU.getCallbackData())
        );

        SendMessage advices = new SendMessage(update.callbackQuery().message().chat().id(), message);
        advices.replyMarkup(inlineKeyboardMarkup);
        return advices;
    }

    /**
     * Метод, выдающий советы пользователю
     *
     * @param update доступное обновление
     * @return сообщение и документ пользователю
     */
    public SendDocument advicesCynologists(Update update) {
        String path = "src/main/resources/adviсe/Cynologist_adviсes.pdf";
        File advices = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                advices);
        sendDocument.caption("В этом докуметне, мы собрали ответы на самые важные вопросы по воспитанию " +
                "и дресеровке вашего питомца \u2191 ");
        return sendDocument;
    }

    /**
     * Метод, выдающий советы пользователю
     *
     * @param update доступное обновление
     * @return сообщение и документ пользователю
     */
    public SendDocument listCynologists(Update update) {
        String path = "src/main/resources/adviсe/List_of_cynologists.pdf";
        File listCynologists = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                listCynologists);
        sendDocument.caption("Мы подобрали небольшой список кинологов  \u2191, которые заслужили наше доверие, вы можете смело " +
                "обращаться к ним за помощью и быть уверенны, что ваш питомец в надежных руках!");
        return sendDocument;
    }

    /**
     * Метод, выдающий советы пользователю
     *
     * @param update доступное обновление
     * @return сообщение и документ пользователю
     */
    public SendDocument reasonsRefusal(Update update) {
        String path = "src/main/resources/adviсe/Reasons_refusal.pdf";
        File reasonsRefusal = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                reasonsRefusal);
        sendDocument.caption("Все причины для отказа сводятся к желанию сделать так, чтобы животное, " +
                "которое уже успело вкусить прелести уличной жизни, не оказалась выброшенным снова. " +
                "Поэтому вряд ли стоит обижаться на работников приюта, " +
                "если они не позволили вам забрать домой понравившегося питомца. \n" +
                "\nС самыми частыми причинами отказов можете ознакомиться в прикрепленном файле \u2191");
        return sendDocument;
    }

}
