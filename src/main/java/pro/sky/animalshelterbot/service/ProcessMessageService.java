package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис ProcessMessageService
 * Сервис для обработки сообщений от пользователя
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class ProcessMessageService {

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Поле: телеграм бот
     */
    private final TelegramBot telegramBot;

    private static final Pattern REPORT_PATTERN = Pattern.compile(
            "([А-яA-z\\s\\d]+):(\\s)([А-яA-z\\s\\d]+)\n" +
                    "([А-яA-z\\s\\d]+):(\\s)([А-яA-z\\s\\d]+)\n" +
                    "([А-яA-z\\s\\d]+):(\\s)([А-яA-z\\s\\d]+)");
    private final ReportService reportService;

    /**
     * Конструктор
     *
     * @param telegramBot   телеграм бот
     * @param reportService
     */
    public ProcessMessageService(TelegramBot telegramBot, ReportService reportService) {
        this.telegramBot = telegramBot;
        this.reportService = reportService;
    }

    /**
     * Обработка принятых сообщений от пользователя
     *
     * @param update доступные обновления
     */
    public void processMessage(Update update) {
        if (update.message().text() == null) {
            return;
        }
        StringBuilder command = new StringBuilder(update.message().text());
        command.delete(0, 1);
        switch (Commands.valueOf(command.toString().toUpperCase())) {
            case START:
                greeting(update);
//                description(update);
                break;
            case INFO:
                info(update);
                break;
            case CALL_VOLUNTEER:
                volunteerMenu(update);
                break;
            default:
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Команда не найдена повторите запрос"));
                break;
        }
    }

    /**
     * Метод, присылающий приветствие для пользователя
     *
     * @param update доступное обновление
     */
    public void greeting(Update update) {
        SendMessage greeting = new SendMessage(update.message().chat().id(),
                "Привет, " + update.message().from().firstName() + "! \uD83D\uDE42 \n" +
                        "\nЯ создан для того, что-бы помочь тебе найти друга, четвероного друга." +
                        "Я живу в приюте для животных и рядом со мной находятся брошенные питомцы, " +
                        "потерявшиеся при переезде, пережившие своих хозяев или рожденные на улице. " +
                        "Поначалу животные в приютах ждут\uD83D\uDC15, что за ними вернутся старые владельцы. \uD83D\uDC64" +
                        "Потом они ждут своих друзей-волонтеров \uD83D\uDC71\uD83C\uDFFB\u200D♂️ \uD83D\uDC71\uD83C\uDFFB\u200D♀️, " +
                        "корм по расписанию⌛, посетителей, которые погладят и почешут за ухом.❤️ " +
                        "Но больше всего приютские подопечные ждут, что их заберут домой.\uD83C\uDFE0 \n");

        telegramBot.execute(greeting);

        String desc = update.message().from().firstName() + ", может ты и есть тот самый хозяин, который подарит новый дом нашему другу?";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("ДА").callbackData(Commands.START_MENU.getCallbackData()),
                new InlineKeyboardButton("Я еще подумаю").callbackData(Commands.NO.getCallbackData())
        );

        SendMessage description = new SendMessage(update.message().chat().id(), desc);
        description.replyMarkup(inlineKeyboardMarkup);

        telegramBot.execute(description);

    }

    /**
     * Метод, выдающий информацию для пользователя
     *
     * @param update доступное обновление
     */
    public void info(Update update) {
        String infoMsg = "«Приют» — слово, от которого становится тоскливо.\uD83D\uDE14" +
                "«Приют для животных» звучит еще более безрадостно.\uD83D\uDE22" +
                "Это место, где живут брошенные питомцы, потерявшиеся при переезде, пережившие своих хозяев или рожденные на улице. \uD83C\uDF27" +
                "\n" +
                "Поначалу животные в приютах ждут\uD83D\uDC15, что за ними вернутся старые владельцы. \uD83D\uDC64" +
                "Потом они ждут своих друзей-волонтеров \uD83D\uDC71\uD83C\uDFFB\u200D♂️ \uD83D\uDC71\uD83C\uDFFB\u200D♀️, " +
                "корм по расписанию⌛, посетителей, которые погладят и почешут за ухом.❤️ " +
                "Но больше всего приютские подопечные ждут, что их заберут домой.\uD83C\uDFE0 " +
                "Если вы решили взять питомца из приюта, то мы с радостью расскажем, как это сделать!\uD83D\uDC4D";

        SendMessage info = new SendMessage(update.message().chat().id(), infoMsg);
        telegramBot.execute(info);
    }

    /**
     * Метод, присылающий информацию по связи с волонтером для пользователя
     *
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private SendMessage volunteerMenu(Update update) {
        SendMessage volunteer = new SendMessage(update.callbackQuery().message().chat().id(), "Волонтер скоро с вами свяжется\uD83D\uDE09");
        return volunteer;
    }

    public void downloadReport(Update update) {
        String text = update.message().caption();
        Matcher matcher = REPORT_PATTERN.matcher(text);

        System.out.println(text);

        if (matcher.matches()) {
            String animalDiet = matcher.group(3);
            String generalInfo = matcher.group(6);
            String changeBehavior = matcher.group(9);

            if (animalDiet != null && generalInfo != null && changeBehavior != null) {
                GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
                GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
                try {
                    File file = getFileResponse.file();
                    file.fileSize();

                    byte[] photo = telegramBot.getFileContent(file);
                    reportService.downloadReport(update.message().chat().id(), animalDiet, generalInfo, changeBehavior, photo);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));

                } catch (IOException e) {
                    logger.error("Ошибка загрузки фото");
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Ошибка загрузки фото"));
                }
            }
            else {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Введены не все данные! Повторите ввод!"));
            }
        }
        else {telegramBot.execute(new SendMessage(update.message().chat().id(),
                "Некорректный формат"));
        }
    }
}
