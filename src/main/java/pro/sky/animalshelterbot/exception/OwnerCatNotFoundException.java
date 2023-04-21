package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение OwnerCatNotFoundException
 * Исключение выбрасывается, когда в БД не найден владелец кота
 * Исключение наследуется от {@link RuntimeException}
 * @author Gubina Marina
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OwnerCatNotFoundException extends RuntimeException{

    public OwnerCatNotFoundException() {
        super("Владелец кота не найден");
    }
}
