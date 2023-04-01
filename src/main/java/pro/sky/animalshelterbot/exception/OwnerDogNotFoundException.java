package pro.sky.animalshelterbot.exception;

/**
 * Исключение OwnerDogNotFoundException
 * Исключение выбрасывается, когда в БД не найден владелец собаки
 * Исключение наследуется от {@link RuntimeException}
 * @author Kilikova Anna
 */
public class OwnerDogNotFoundException extends RuntimeException{
    /**
     * Конструктор исключения
     * Выброс сообщения о том, что владелец собаки не найден
     */
    public OwnerDogNotFoundException() {
        super("Такого потенциального владельца в базе данных не найденно");
    }
}
