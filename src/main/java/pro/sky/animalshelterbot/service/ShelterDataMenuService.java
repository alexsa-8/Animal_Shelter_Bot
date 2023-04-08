package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
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
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

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
     * @return документ с рекомендации по технике безопасности
     */
    public SendDocument shelterRecommendation(Update update) {
        String path = "src/main/resources/shelterInfo/Safety_in_shelter.pdf";
        File recommendation = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        sendDocument.caption("Рекомендации по технике безопасности на территории " +
                "приюта в прикрепленном документе \u2191 ");
        return sendDocument;
    }

    /**
     * Метод, присылающий информацию по приюту для пользователя
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendPhoto shelterData(Update update) {
        String dataMessage = "  Доброго времени суток! Наши контактные данные:" +
                "\n Адрес: г. Астана, Сарыарка район, Коктал ж/м, ул. Аккорган, 5в. " +
                " \n Часы работы приюта: ежедневно с 11:00 до 18:00 \n Тел.: +7‒702‒481‒01‒58" +
                " \n Email: animalshelterastaba@gmail.com  \n";
        String path = "src/main/resources/shelterInfo/map.jpg";
        File map = new File(path);
        SendPhoto photo = new SendPhoto(update.callbackQuery().message().chat().id(), map);
        photo.caption(dataMessage + " Схема проезда до нашего приюта \u2191");
        return photo;
    }


}
