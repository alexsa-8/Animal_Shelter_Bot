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
        if (ProcessCallbackQueryService.isDog()) {
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(Commands.ADVICES.getDescription())
                            .callbackData(Commands.ADVICES.getCallbackData())
            );
        } else {
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(Commands.REASONS_REFUSAL.getDescription())
                            .callbackData(Commands.REASONS_REFUSAL.getCallbackData()));
        }
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CONTACT_DETAILS.getDescription())
                        .callbackData(Commands.CONTACT_DETAILS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.VOLUNTEER.getDescription())
                        .callbackData(Commands.VOLUNTEER.getCallbackData())

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

        logger.info("Launched method: dating_rules, for user with id: " +
                update.callbackQuery().message().chat().id());

        String dogDatingRules = "Рекомендуем начать процесс знакомства и общения с будущим подопечным заранее. " +
                "После того, как вы сделали выбор, начните навещать животное в приюте, строить с ним доверительные отношения. " +
                "Приносить собаке лакомства, начать выводить её на прогулки, аккуратно гладить. " +
                "Это должно происходить спокойно и ненавязчиво, без какого-либо давления с вашей стороны. " +
                "Когда животное начнёт вас узнавать, вилять хвостом при встрече, и позволит с ним играть, " +
                "можно устроить пару гостевых посещений, приведя собаку в дом. " +
                "Это поможет собаке в дальнейшем более легкому знакомству с незнакомой обстановкой и привыканию к новому дому";

        String kittenDatingRules = "Что нужно знать перед тем как взять кота из приюта?\n" +
                "✅Узнайте возраст и особенности поведения;\n" +
                "✅Расспросите о прошлом котика: как он попал в приют, какие у него были хозяева;\n" +
                "✅Как он себя обычно ведет;\n" +
                "✅Как реагирует, если оставить одного;\n" +
                "✅Как себя ведет с другими животными, если у вас дома уже живет кто-то;\n" +
                "✅Бывает ли на улице и любит ли сбегать туда.\n";

        SendMessage message;
        if (ProcessCallbackQueryService.isDog()) {
            message = new SendMessage(update.callbackQuery().message().chat().id(),
                    dogDatingRules);
        } else {
            message = new SendMessage(update.callbackQuery().message().chat().id(),
                    kittenDatingRules);
        }

        return message;
    }

    /**
     * Метод, выдающий список документов для пользователя
     *
     * @param update доступное обновление
     * @return сообщение c документом пользователю
     */
    public SendDocument listDocuments(Update update) {

        logger.info("Launched method: list_documents, for user with id: " +
                update.callbackQuery().message().chat().id());

        String path = "src/main/resources/list_documents/Take_the_dog.pdf";
        File listDocuments = new File(path);

        SendDocument sendDocument;

        if (ProcessCallbackQueryService.isDog()) {
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    listDocuments);
            sendDocument.caption("Из неообходимых документов Вам потребуется только ПАСПОРТ\n" +
                    "\nНо прежде чем вы соберетесь на такой важный шаг, не только для себя, " +
                    "но и для вашего будущего питомца, просим Вас ознакомиться с информацие в прикрепленном документе \u2191");
        } else {
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    listDocuments);
            sendDocument.caption(" Все про документы для оформления котенка");
        }

        return sendDocument;
    }

    /**
     * Метод для сохранения контактных данных пользователя
     *
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    public SendMessage contactDetails(Update update) {

        logger.info("Launched method: contact_details, for user with id: " +
                update.callbackQuery().message().chat().id());

        ReplyKeyboardMarkup msg = new ReplyKeyboardMarkup(new KeyboardButton("Оставить контактные данные").requestContact(true));
        msg.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(update.callbackQuery().message().chat().id(),
                "Оставьте свой контакт и мы свяжемся с вами в ближайшее время");
        sendMessage.replyMarkup(msg);
        return sendMessage;
    }
}
