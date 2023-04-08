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
 * Сервис ShelterInfoMenuService
 * Сервис для меню рекомендаций
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class RecommendationMenuService {

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
    public SendMessage recommendationMenu(Update update) {
        String message = "Список рекомендаций";
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_TRANSPORTATION.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_TRANSPORTATION.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_PUPPY.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_PUPPY.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_DOG.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_DOG.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_DISABLED_DOG.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_DISABLED_DOG.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK_TO_ANIMAL_MENU.getDescription())
                        .callbackData(Commands.BACK_TO_ANIMAL_MENU.getCallbackData())
        );

        SendMessage recom = new SendMessage(update.callbackQuery().message().chat().id(), message);
        recom.replyMarkup(inlineKeyboardMarkup);
        return recom;
    }

    /**
     * Метод получения рекомендации по транспортировке собаки
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsTransportation(Update update) {
        String path = "src/main/resources/recommendations/Recommendations_of_Transportation.pdf";
        File recommendation = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        sendDocument.caption("С рекомендации по общим правилам транспортировки собак Вы можете ознакомиться  " +
                "  в прикрепленном документе \u2191 ");
        return sendDocument;
    }

    /**
     * Метод получения рекомендации по обустройству дома для взрослой собаки
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsDog(Update update) {
        String path = "src/main/resources/recommendations/Recommendations_for_Dog.pdf";
        File recommendation = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        sendDocument.caption("С рекомендации по обустройству дома для взрослой собаки Вы можете ознакомиться  " +
                "  в прикрепленном документе \u2191, а также посмотрите пункт Рекомендации по уходу за щенком");
        return sendDocument;
    }

    /**
     * Метод получения рекомендации по обустройству дома для щенка
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsPuppy(Update update) {
        String path = "src/main/resources/recommendations/Recommendations_for_Puppy.pdf";
        File recommendation = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        sendDocument.caption("С рекомендации по обустройству дома для щенка Вы можете ознакомиться  " +
                "  в прикрепленном документе \u2191 ");
        return sendDocument;
    }

    /**
     * Метод получения рекомендации по обустройству дома для собаки с ОВЗ
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsDisabledDog(Update update) {
        String path = "src/main/resources/recommendations/Recommendations_for_Disabled_Dog.pdf";
        File recommendation = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        sendDocument.caption("С рекомендации по обустройству дома для собаки с ограниченными возможностями " +
                "Вы можете ознакомиться в прикрепленном документе \u2191 ");
        return sendDocument;
    }

}
