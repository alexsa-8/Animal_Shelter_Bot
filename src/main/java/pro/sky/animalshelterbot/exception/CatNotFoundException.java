package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение CatNotFoundException
 * Исключение выбрасывается, когда в БД не найден кот
 * Исключение наследуется от {@link RuntimeException}
 * @author Gubina Marina
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatNotFoundException extends RuntimeException{
     /**
     * Конструктор исключения
     * Выброс сообщения о том, что кот не найден
     */
    public CatNotFoundException() {
        super("Кот не найден в БД");
    }
}
