package pro.sky.animalshelterbot.constant;

public enum OwnerStatus {
    PROBATION("На испытательном сроке"),
    IN_SEARCH("В поиске питомца"),
    APPROVED("Одобрен"),
    IN_BLACK_LIST("Не прошел испытательный срок, в ЧС");

    private final String description;

    OwnerStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
