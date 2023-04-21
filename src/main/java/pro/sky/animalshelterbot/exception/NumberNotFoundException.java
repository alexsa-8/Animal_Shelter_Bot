package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NumberNotFoundException extends RuntimeException{
    public NumberNotFoundException() {
        super("Вы ввели неверный номер команды");
    }

}
