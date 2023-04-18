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
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    /**
     * Поле: объект телеграм бот
     */
    private final TelegramBot telegramBot;

    /**
     * Поле: объект сервиса отчетов
     */
    private final ReportService reportService;

    private static final Pattern REPORT_PATTERN = Pattern.compile(
            "([А-яA-z\\s\\d]+):(\\s)([А-яA-z\\s\\d]+)\n" +
                    "([А-яA-z\\s\\d]+):(\\s)([А-яA-z\\s\\d]+)\n" +
                    "([А-яA-z\\s\\d]+):(\\s)([А-яA-z\\s\\d]+)");

    /**
     * Конструктор
     * @param telegramBot       телеграм бот
     * @param reportService     сервис по отчетам
     */
    public SendReportMenuService(TelegramBot telegramBot, ReportService reportService) {
        this.telegramBot = telegramBot;
        this.reportService = reportService;
    }

    /**
     * Метод, вызывающий подменю по отчетам
     *
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendMessage submitReportMenu(Update update) {
        SendMessage report = new SendMessage(update.callbackQuery().message().chat().id(), "Отправка отчета");
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

        logger.info("Launched method: report_form, for user with id: " +
                update.callbackQuery().message().chat().id());

        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "ЗАГРУЗИТЕ ОТЧЕТ В ФОРМАТЕ: \n \n" +
                        "Рацион: данные о рационе \n"+
                        "Информация: общая информация \n"+
                        "Привычки: данные о изменении привычек \n"+
                        "И прикрепите фото к отчету.");
        return message;
    }

    /**
     * Метод сохранения отчета из чата телеграм
     * @param update    доступное обновление
     */
    public void downloadReport(Update update) {

        logger.info("Launched method: download_report, for user with id: " +
                update.callbackQuery().message().chat().id());

        String text = update.message().caption();
        Matcher matcher = REPORT_PATTERN.matcher(text);

        logger.info("Accepted data [" + text + "]");

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
                    LocalDate date = LocalDate.now();
                    reportService.downloadReport(update.message().chat().id(), animalDiet, generalInfo,
                            changeBehavior, photo, LocalDate.from(date.atStartOfDay()));
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
