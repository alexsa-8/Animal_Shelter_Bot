package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * Сервис TelegramBotUpdatesListener
 * Сервис для обработки доступных обновлений в чате
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @author Marina Gubina
 * @see UpdatesListener
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Поле: телеграм бот
     */
    private final TelegramBot telegramBot;

    /**
     * Поле: сервис по обработке сообщений
     */
    private final ProcessMessageService processMessageService;

    /**
     * Поле: сервис по обработке нажатия кнопок
     */
    private final ProcessCallbackQueryService processCallbackQueryService;

    /**
     * Поле: сервис по обработке отчетов
     */
    private final SendReportMenuService sendReportMenuService;

    /**
     * Конструктор
     *
     * @param telegramBot                 телеграм бот
     * @param processMessageService       сервис по обработке сообщений
     * @param processCallbackQueryService сервис по обработке нажатия кнопок
     * @param sendReportMenuService       сервис по обработке отчетов
     */
    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      ProcessMessageService processMessageService,
                                      ProcessCallbackQueryService processCallbackQueryService, SendReportMenuService sendReportMenuService) {

        this.telegramBot = telegramBot;
        this.processMessageService = processMessageService;
        this.processCallbackQueryService = processCallbackQueryService;
        this.sendReportMenuService = sendReportMenuService;

        telegramBot.execute(new SetMyCommands(
                new BotCommand(Commands.START.getTitle(), Commands.START.getDescription()),
                new BotCommand(Commands.INFO.getTitle(), Commands.INFO.getDescription()),
                new BotCommand(Commands.CALL_VOLUNTEER.getTitle(), Commands.CALL_VOLUNTEER.getDescription())
        ));
    }

    /**
     * Метод, выполняющийся после инициализации объекта, задает обработчик обновлений
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обработчик обратного вызова с доступными обновлениями
     *
     * @param updates доступные обновления
     * @return id последнего обработанного обновления, которое не нужно доставлять повторно
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> processUpdate(update));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Обработчик обратного вызова с доступными обновлениями,
     * проверяет было отправлено сообщение или нажата кнопка
     *
     * @param update доступные обновления
     */
    private void processUpdate(Update update) {
        if (update.message() != null) {
            if(update.message().photo() != null || update.message().caption()!=null){
                sendReportMenuService.downloadReport(update);
            }
            else {processMessageService.processMessage(update);}
        } else if (update.callbackQuery() != null) {
            processCallbackQueryService.processCallbackQuery(update);
        }
    }

}
