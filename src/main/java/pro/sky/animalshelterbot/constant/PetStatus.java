package pro.sky.animalshelterbot.constant;

/**
 * Enum PetStatus
 * Используется для констант статусов животных, находящихся в приюте
 * @author Kilikova Anna
 */
public enum PetStatus {
    FREE("Ищет хозяина"),
    BUSY("Нашел(нашла) дом");

    /**
     * Поле "Описание"
     */
    private final String description;

    /**
     * Конструктор создания статуса животного
     * @param description описание статуса
     */
    PetStatus(String description) {
        this.description = description;
    }

    /**
     * Получение описания статуса
     * @return описание статуса
     */
    public String getDescription() {
        return description;
    }
}
