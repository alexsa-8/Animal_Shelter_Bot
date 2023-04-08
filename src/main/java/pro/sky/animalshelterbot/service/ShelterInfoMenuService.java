package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;

import java.io.File;

/**
 * Сервис ShelterInfoMenuService
 * Сервис для информационного меню приюта
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class ShelterInfoMenuService {

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Метод, вызывающий подменю по животным
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendMessage animalInfoMenu(Update update) {
        SendMessage animalInfo = new SendMessage(update.callbackQuery().message().chat().id(),
                "Вы можете ознакомиться со всеми особенностями, с которыми нам с вами предстоид столкнуться, " +
                        "прежде чем вы сможете найти себе нового друга");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.DATING_RULES.getDescription())
                        .callbackData(Commands.DATING_RULES.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.LIST_DOCUMENTS.getDescription())
                        .callbackData(Commands.LIST_DOCUMENTS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.ADVICES.getDescription())
                        .callbackData(Commands.ADVICES.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CONTACT_DETAILS.getDescription())
                        .callbackData(Commands.CONTACT_DETAILS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CALL_VOLUNTEER.getDescription())
                        .callbackData(Commands.CALL_VOLUNTEER.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK.getDescription())
                        .callbackData(Commands.BACK.getCallbackData())
        );

        animalInfo.replyMarkup(inlineKeyboardMarkup);

        return animalInfo;
    }

    /**
     * Метод, выдающий информацию по знакомству с питомцем для пользователя
     *
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    public SendMessage datingRules(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Тут все про знакомство с питомцем");
        return message;
    }

    /**
     * Метод, выдающий список документов для пользователя
     * @param update доступное обновление
     * @return сообщение c документом пользователю
     */
    public SendDocument listDocuments(Update update) {
        String path = "src/main/resources/list_documents/Take_the_dog.pdf";
        File listDocuments = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                listDocuments);
        sendDocument.caption("Из неообходимых документов Вам потребуется только ПАСПОРТ\n" +
                "\nНо прежде чем вы соберетесь на такой важный шаг, не только для себя, " +
                "но и для вашего будущего питомца, просим Вас ознакомиться с информацие в прикрепленном документе \u2191");

        return sendDocument;
    }

    /**
     * Метод для сохранения контактных данных пользователя
     *
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    public SendMessage contactDetails(Update update) {

        ReplyKeyboardMarkup msg = new ReplyKeyboardMarkup(new KeyboardButton("Оставить контактные данные").requestContact(true));
        msg.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(update.callbackQuery().message().chat().id(),
                "Оставьте свой контакт и мы свяжемся с вами в ближайшее время");
        sendMessage.replyMarkup(msg);
        return sendMessage;
    }
}
