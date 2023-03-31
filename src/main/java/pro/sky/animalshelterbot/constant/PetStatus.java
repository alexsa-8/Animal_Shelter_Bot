package pro.sky.animalshelterbot.constant;

public enum PetStatus {
    FREE("Ищет хозяина"),
    BUSY("Нашел(нашла) дом");

    private final String description;


    PetStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
