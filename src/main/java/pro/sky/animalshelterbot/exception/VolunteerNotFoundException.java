package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение VolunteerNotFoundException
 * Исключение выбрасывается, когда в БД не найден волонтёр
 * Исключение наследуется от {@link RuntimeException}
 * @author Rogozin Alexandr
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VolunteerNotFoundException extends RuntimeException {
    /**
     * Конструктор исключения
     * Выброс сообщения, что волонтёр не найден
     */
    public VolunteerNotFoundException() {
        super("Такой волонтёр в базе данных не найден");
    }
}
