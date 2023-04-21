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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.Report;
import pro.sky.animalshelterbot.entity.Volunteer;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;
import pro.sky.animalshelterbot.repository.ReportRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;


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
    private Volunteer volunteer;
    private final ReportRepository repository;

    private static final Pattern REPORT_PATTERN = Pattern.compile(
            "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)\n" +
                    "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)\n" +
                    "([А-яA-z\\s\\d\\D]+):(\\s)([А-яA-z\\s\\d\\D]+)");

    private final OwnerDogRepository ownerDogRepository;

    /**
     * Конструктор
     *
     * @param telegramBot   телеграм бот
     * @param reportService сервис по отчета
     * @param repository
     */
    public SendReportMenuService(TelegramBot telegramBot, ReportService reportService, ReportRepository repository,
                                 OwnerDogRepository ownerDogRepository) {
        this.telegramBot = telegramBot;
        this.reportService = reportService;
        this.repository = repository;
        this.ownerDogRepository = ownerDogRepository;
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
                new InlineKeyboardButton(Commands.VOLUNTEER.getDescription())
                        .callbackData(Commands.VOLUNTEER.getCallbackData())

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
                        "Рацион: данные о рационе \n" +
                        "Информация: общая информация \n" +
                        "Привычки: данные о изменении привычек \n" +
                        "И прикрепите фото к отчету.");
        return message;
    }

    /**
     * Метод сохранения отчета из чата телеграм
     *
     * @param update доступное обновление
     */
    public void downloadReport(Update update) {

        logger.info("Launched method: download_report, for user with id: " +
                update.message().chat().id());

        String text = update.message().caption();
        Matcher matcher = REPORT_PATTERN.matcher(text);

        logger.info("Accepted data [" + text + "]");

        if (matcher.matches()) {
            String animalDiet = matcher.group(3);
            String generalInfo = matcher.group(6);
            String changeBehavior = matcher.group(9);

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
                        "Введены не все данные, заполните все поля в отчете! Повторите ввод!"));
        }
    }

    @Scheduled(cron = "0 0/3 * * * *")
    public void sendNotificationDog() {
        logger.info("Requests report to OwnerDog");
        for (Report report : reportService.findNewReports()) {
            Long ownerDogId = report.getOwnerDog().getId();
            long daysBetween = DAYS.between(LocalDate.now(), report.getDateMessage());
            if (report.getDateMessage().isBefore(LocalDate.now().minusDays(1))) {
                SendMessage sendMessage = new SendMessage(volunteer.getChatId(), "Отчет о собаке "
                        + report.getOwnerDog().getDog().getName() + " (id: " + report.getOwnerDog().getDog().getId() + ") от владельца "
                        + report.getOwnerDog().getName() + " (id: " + ownerDogId + ") не поступал уже " + daysBetween + " дней. "
                        + "Дата последнего отчета: " + report.getDateMessage());
                telegramBot.execute(sendMessage);
            }
            if (report.getDateMessage().equals(LocalDate.now().minusDays(1))) {
                SendMessage sendToOwner = new SendMessage(report.getOwnerDog().getChatId(), "Дорогой владелец, " +
                        "не забудь сегодня отправить отчет");
                telegramBot.execute(sendToOwner);
            }
        }
        for (Report report : reportService.findOldReports()) {
            Long ownerDogId = report.getOwnerDog().getId();
            if (report.getDateMessage().equals(LocalDate.now().minusDays(30))) {
                repository.save(report);
                report.getOwnerDog().setStatus(OwnerStatus.APPROVED);
                SendMessage sendMessage = new SendMessage(ownerDogId, report.getOwnerDog().getName() + "! поздравляем," +
                        "испытательный срок в 30 дней для собаки " + report.getOwnerDog().getDog().getName() +
                        " (id: " + report.getOwnerDog().getDog().getId() + ") закончен");
                telegramBot.execute(sendMessage);
            }
        }
    }


}
