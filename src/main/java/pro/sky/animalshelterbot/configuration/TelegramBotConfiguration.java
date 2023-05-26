package pro.sky.animalshelterbot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс TelegramBotConfiguration
 * Используется для создания Bean для запуска телеграм-бота
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 */
@Configuration
public class TelegramBotConfiguration {

    /**
     * Поле "Token"
     * Токен прописывается в application.properties
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создание Bean
     * @return запуск бота
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
