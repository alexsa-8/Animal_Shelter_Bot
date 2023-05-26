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
import pro.sky.animalshelterbot.repository.UserRepository;

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
     * Поле: репозиторий пользователей
     */
    private final UserRepository userRepository;

    /**
     * Конструктор
     *
     * @param userRepository репозиторий пользователей
     */
    public RecommendationMenuService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
     *
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsTransportation(Update update) {

        logger.info("Launched method: recommendations_transportation, for user with id: " +
                update.callbackQuery().message().chat().id());

        String pathDog = "src/main/resources/recommendations/Recommendations_of_Transportation.pdf";
        String pathKitten = "src/main/resources/recommendations/Recommendations_of_Transportation_Cats.pdf";
        File recommendation;
        SendDocument sendDocument;
        if (userRepository.findUserByChatId(update.callbackQuery().message().chat().id()).isDog()) {
            recommendation = new File(pathDog);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по общим правилам транспортировки собак Вы можете ознакомиться  " +
                    "  в прикрепленном документе \u2191 ");
        } else {
            recommendation = new File(pathKitten);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по общим правилам транспортировки кошек Вы можете ознакомиться  " +
                    "  в прикрепленном документе \u2191 ");
        }

        return sendDocument;
    }

    /**
     * Метод получения рекомендации по обустройству дома для взрослой собаки/кошки
     *
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendations(Update update) {

        logger.info("Launched method: recommendations, for user with id: " +
                update.callbackQuery().message().chat().id());

        String pathDog = "src/main/resources/recommendations/Recommendations_for_Dog.pdf";
        String pathKitten = "src/main/resources/recommendations/Recommendations_for_Cat.pdf";
        File recommendation;
        SendDocument sendDocument;

        if (userRepository.findUserByChatId(update.callbackQuery().message().chat().id()).isDog()) {
            recommendation = new File(pathDog);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по обустройству дома для взрослой собаки Вы можете ознакомиться  " +
                    "  в прикрепленном документе \u2191, а также посмотрите пункт Рекомендации по уходу за щенком");
        } else {
            recommendation = new File(pathKitten);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по обустройству дома для взрослой кошки/кота Вы можете ознакомиться  " +
                    "  в прикрепленном документе \u2191, а также посмотрите пункт Рекомендации по уходу за котенком");
        }

        return sendDocument;
    }

    /**
     * Метод получения рекомендации по обустройству дома для щенка/котенка
     *
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsPuppy(Update update) {

        logger.info("Launched method: recommendations_puppy, for user with id: " +
                update.callbackQuery().message().chat().id());

        String pathDog = "src/main/resources/recommendations/Recommendations_for_Puppy.pdf";
        String pathKitten = "src/main/resources/recommendations/Recommendations_for_Kittens.pdf";
        File recommendation;
        SendDocument sendDocument;
        if (userRepository.findUserByChatId(update.callbackQuery().message().chat().id()).isDog()) {
            recommendation = new File(pathDog);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по обустройству дома для щенка Вы можете ознакомиться  " +
                    "  в прикрепленном документе \u2191 ");
        } else {
            recommendation = new File(pathKitten);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по обустройству дома для котенка Вы можете ознакомиться  " +
                    "  в прикрепленном документе \u2191 ");
        }

        return sendDocument;
    }

    /**
     * Метод получения рекомендации по обустройству дома для собаки/кошки с ОВЗ
     *
     * @param update доступное обновление
     * @return документ формата pdf
     */
    public SendDocument recommendationsDisabled(Update update) {

        logger.info("Launched method: recommendations_disabled, for user with id: " +
                update.callbackQuery().message().chat().id());

        String pathDog = "src/main/resources/recommendations/Recommendations_for_Disabled_Dog.pdf";
        String pathKitten = "src/main/resources/recommendations/Recommendations_for_Disabled_Cat.pdf";
        File recommendation;
        SendDocument sendDocument;

        if (userRepository.findUserByChatId(update.callbackQuery().message().chat().id()).isDog()) {
            recommendation = new File(pathDog);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по обустройству дома для собаки с ограниченными возможностями " +
                    "Вы можете ознакомиться в прикрепленном документе \u2191 ");
        } else {
            recommendation = new File(pathKitten);
            sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("С рекомендации по обустройству дома для кошки с ограниченными возможностями " +
                    "Вы можете ознакомиться в прикрепленном документе \u2191 ");
        }

        return sendDocument;
    }

}
