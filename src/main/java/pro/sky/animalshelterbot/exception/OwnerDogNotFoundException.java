package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение OwnerDogNotFoundException
 * Исключение выбрасывается, когда в БД не найден владелец собаки
 * Исключение наследуется от {@link RuntimeException}
 * @author Kilikova Anna
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OwnerDogNotFoundException extends RuntimeException{
    /**
     * Конструктор исключения
     * Выброс сообщения о том, что владелец собаки не найден
     */
    public OwnerDogNotFoundException() {
        super("Такого потенциального владельца в базе данных не найденно");
    }
}
