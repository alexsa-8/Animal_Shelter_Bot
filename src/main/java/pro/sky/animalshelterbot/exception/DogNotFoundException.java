package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение DogNotFoundException
 * Исключение выбрасывается, когда в БД не найдена собака
 * Исключение наследуется от {@link RuntimeException}
 * @author Kilikova Anna
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DogNotFoundException extends RuntimeException{

    /**
     * Конструктор исключения
     * Выброс сообщения о том, что собака не найдена
     */
    public DogNotFoundException() {
        super("Такой собаки нет в базе");
    }
}
