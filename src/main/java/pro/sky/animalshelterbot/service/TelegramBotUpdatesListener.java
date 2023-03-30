package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    public enum Command {
        START("/start"),
        INFO("/info"),
        VOLUNTEER("/volunteer");

        private String title;

        Command(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        telegramBot.execute(new SetMyCommands(
                new BotCommand(Command.START.getTitle(), "Get a welcome message"),
                new BotCommand(Command.INFO.getTitle(), "Get detailed information on all the features of the bot"),
                new BotCommand(Command.VOLUNTEER.getTitle(), "Call a volunteer")
        ));
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null) {
                if (update.message().text().equals(Command.START.getTitle())) {
                    greeting(update);
                    description(update);
                } else if (update.message().text().equals(Command.INFO.getTitle())) {
                    info(update);
                } else if (update.message().text().equals(Command.VOLUNTEER.getTitle())) {
                    volunteer(update);
                } else {
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Команда не найдена повторите запрос"));
                }
            } else if (update.message() == null) {
                telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                        update.callbackQuery().data()));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    // method sends a greeting to the user
    public void greeting(Update update) {
        logger.info("Greeting to " + update.message().text());
        SendMessage greeting = new SendMessage(update.message().chat().id(),
                "Привет, " + update.message().from().firstName() + "! \uD83D\uDE42");

        telegramBot.execute(greeting);
    }

    public void description(Update update) {
        logger.info("Description to " + update.message().text());
        String desc = "Я создан для того, что-бы помочь тебе найти друга, четвероного друга." +
                " Я живу в приюте для животных и рядом со мной находятся брошенные питомцы, " +
                "потерявшиеся при переезде, пережившие своих хозяев или рожденные на улице. " +
                "Поначалу животные в приютах ждут\uD83D\uDC15, что за ними вернутся старые владельцы. \uD83D\uDC64" +
                "Потом они ждут своих друзей-волонтеров \uD83D\uDC71\uD83C\uDFFB\u200D♂️ \uD83D\uDC71\uD83C\uDFFB\u200D♀️, " +
                "корм по расписанию⌛, посетителей, которые погладят и почешут за ухом.❤️ " +
                "Но больше всего приютские подопечные ждут, что их заберут домой.\uD83C\uDFE0 \n" +
                "\nМоже ты и есть тот самый хозяин, который подарит новый дом нашему другу?";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("ДА").callbackData("Вы нажали кнопку да"),
                new InlineKeyboardButton("Я еще подумаю").callbackData("Мы будем тебя ждать!")
        );


        SendMessage description = new SendMessage(update.message().chat().id(), desc);
        description.replyMarkup(inlineKeyboardMarkup);

        telegramBot.execute(description);
    }

    // method sends information to the user
    public void info(Update update) {
        logger.info("Info to " + update.message().text());
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

    // The method calls the volunteer
    public void volunteer(Update update) {
        logger.info("volunteer to " + update.message().text());
        SendMessage volunteer = new SendMessage(update.message().chat().id(), "Волонтер скоро с вами свяжется\uD83D\uDE09");

        telegramBot.execute(volunteer);
    }

}
