package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DogNotFoundException extends RuntimeException{
    public DogNotFoundException() {
        super("Такой собаки нет в базе");
    }
}
