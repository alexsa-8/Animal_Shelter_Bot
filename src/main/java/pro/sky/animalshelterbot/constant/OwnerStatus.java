package pro.sky.animalshelterbot.constant;

/**
 * Enum OwnerStatus
 * Используется для констант статусов владельцев собак
 * @author Kilikova Anna
 */
public enum OwnerStatus {
    IN_SEARCH("В поиске питомца"),
    PROBATION("На испытательном сроке"),
    APPROVED("Одобрен"),
    IN_BLACK_LIST("Не прошел испытательный срок, в ЧС");

    /**
     * Поле "Описание"
     */
    private final String description;

    /**
     * Конструктор создания статуса владельца
     * @param description описание статуса владельца
     */
    OwnerStatus(String description) {
        this.description = description;
    }

    /**
     * Получение описания статуса
     * @return описание
     */
    public String getDescription() {
        return description;
    }
}
