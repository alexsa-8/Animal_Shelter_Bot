package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;

import java.io.File;

@Service
public class ShelterDataMenuService {

    /**
     * Поле: телеграм бот
     */
    private final TelegramBot telegramBot;

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public ShelterDataMenuService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод для запуска меню
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendMessage shelterInfoMenu(Update update) {
        SendMessage shelterInfo = new SendMessage(update.callbackQuery().message().chat().id(),
                "Здесь вы можете получить всю информацию по приюту.");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.SHELTER_DATA.getDescription())
                        .callbackData(Commands.SHELTER_DATA.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.SHELTER_RECOMMENDATIONS.getDescription())
                        .callbackData(Commands.SHELTER_RECOMMENDATIONS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CAR_PASS.getDescription())
                        .callbackData(Commands.CAR_PASS.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK.getDescription())
                        .callbackData(Commands.BACK.getCallbackData())
        );
        shelterInfo.replyMarkup(inlineKeyboardMarkup);
        return shelterInfo;
    }

    /**
     * Метод, выдающий рекомендации о технике безопасности пользователю
     *
     * @param update доступное обновление
     */
    public void shelterRecommendation(Update update) {

        logger.info("Launched method: shelter_recommendation, for user with id: " +
                update.callbackQuery().message().chat().id());

        String pathDog = "src/main/resources/shelterInfo/Safety_in_shelter.pdf";
        String informationCatShelter = "✅ Работники и посетители приюта обязаны соблюдать правила личной гигиены, " +
                "в том числе мыть руки с дезинфицирующими средствами после общения с животными.\n" +
                "❌ Нахождение на территории в излишне возбужденном состоянии, а также в состоянии алкогольного, " +
                "наркотического или медикаментозного опьянения строго запрещено.";


        if (ProcessCallbackQueryService.isDog()) {
            File recommendation = new File(pathDog);
            SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                    recommendation);
            sendDocument.caption("Рекомендации по технике безопасности на территории " +
                    "приюта в прикрепленном документе \u2191 ");
            telegramBot.execute(sendDocument);
        } else {
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(), informationCatShelter));
        }
    }

    /**
     * Метод, присылающий информацию по приюту для пользователя
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendPhoto shelterData(Update update) {

        logger.info("Launched method: shelter_data, for user with id: " +
                update.callbackQuery().message().chat().id());

        String dataMessageDogShelter = "  Доброго времени суток! Наши контактные данные:" +
                "\n Адрес: г. Астана, Сарыарка район, Коктал ж/м, ул. Аккорган, 5в. " +
                " \n Часы работы приюта: ежедневно с 11:00 до 18:00 \n Тел.: +7‒702‒481‒01‒58" +
                " \n Email: animalshelterastaba@gmail.com  \n";

        String dataMessageKittenShelter = "  Доброго времени суток! Наши контактные данные:" +
                "\n Адрес: г. Алматы, ул. Спортивная дом 3 " +
                " \n Часы работы приюта: ежедневно с 10:00 до 18:00 \n Тел.: +7‒702‒262‒39‒82" +
                " \n Сайт kotopesoff.kz  \n"+
                " \n Email: wotdpress@kotopesoff.kz  \n";

        String pathDog = "src/main/resources/shelterInfo/map.jpg";
        String pathKitten = "src/main/resources/shelterInfo/mapCatShelter.jpg";
        File map;
        SendPhoto photo;

        if (ProcessCallbackQueryService.isDog()) {
            map = new File(pathDog);
            photo = new SendPhoto(update.callbackQuery().message().chat().id(), map);
            photo.caption(dataMessageDogShelter + " Схема проезда до нашего приюта \u2191");
        } else {
            map = new File(pathKitten);
            photo = new SendPhoto(update.callbackQuery().message().chat().id(), map);
            photo.caption(dataMessageKittenShelter + " Схема проезда до нашего приюта \u2191");
        }

        return photo;
    }

    /**
     * Метод, выдающий номер телефона для оформления пропуска на машину
     *
     * @param update доступное обновление
     * @return номер телефона для оформления пропуска
     */
    public SendMessage carPass(Update update){

        String contactDogShelter = "+7‒702‒481‒01‒58";
        String contactCatShelter = "+7‒702‒262‒39‒82";
        String pass;

        if (ProcessCallbackQueryService.isDog()) {
            pass = contactDogShelter;
        } else {
            pass = contactCatShelter;
        }

        String registrationPass = "Позвоните по этому номеру телефона и оформите пропуск на машину: " + pass;
        SendMessage sendMessage = new SendMessage(update.callbackQuery().message().chat().id(),registrationPass);
        return sendMessage;
    }

    /**
     * Метод, выдающий номер телефона для оформления пропуска на машину
     *
     * @param update доступное обновление
     * @return номер телефона для оформления пропуска
     */
    public SendChatAction choosingAPet(Update update){

        String petDog = "https://kotopesoff.kz/pets/dogs";
        String petCat = "https://kotopesoff.kz/pets/cats";
        String pet;

        if (ProcessCallbackQueryService.isDog()) {
            pet = petDog;
        } else {
            pet = petCat;
        }

        String selectedPet = "Нажав на эту ссылку Вы попадёте в приют, где можете выбрать питомца: " + pet;
        SendChatAction sendChatAction = new SendChatAction  (update.callbackQuery().message().chat().id(),selectedPet);
        return sendChatAction;
    }
}
