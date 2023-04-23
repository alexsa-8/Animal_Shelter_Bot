package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProbationNotSpecifiedException extends RuntimeException{
    public ProbationNotSpecifiedException() {
        super("Испытательный срок не указан");
    }
}
