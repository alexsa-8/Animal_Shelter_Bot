package pro.sky.animalshelterbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение ReportNotFoundException
 * Исключение выбрасывается, когда в БД не найден отчет
 * Исключение наследуется от {@link RuntimeException}
 * @author Gubina Marina
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException{

    /**
     * Конструктор исключения, выбрасывает сообщение о том, что отчет не найден  БД
     */
    public ReportNotFoundException(){
        super("Отчет не найден в базе данных");
    }
}
