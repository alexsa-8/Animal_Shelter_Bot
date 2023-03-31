package pro.sky.animalshelterbot.exception;

public class OwnerDogNotFoundException extends RuntimeException{
    public OwnerDogNotFoundException() {
        super("Такого потенциального владельца в базе данных не найденно");
    }
}
