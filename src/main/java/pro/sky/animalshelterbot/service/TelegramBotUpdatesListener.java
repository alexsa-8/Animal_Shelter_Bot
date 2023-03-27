package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            SendMessage greeting = new SendMessage(update.message().chat().id(),
                    "Привет, " + update.message().from().firstName() + "! \uD83D\uDE42");

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
            String message = update.message().text();

            logger.info("Processing update: {}", update);

            if (message != null && update.message().text().equals("/start")) {
                telegramBot.execute(greeting);
                telegramBot.execute(info);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
