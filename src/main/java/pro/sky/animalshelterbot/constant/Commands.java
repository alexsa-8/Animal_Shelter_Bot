package pro.sky.animalshelterbot.constant;

public enum Commands {
    CALL_VOLUNTEER("/volunteer","Позвать волонтера"),
    INFO("/info", "Информация о приюте"),
    RECOMMENDATIONS("/recommendations","Рекомендации"),
    SHELTER_ADDRESS("/address", "Адрес приюта"),
    SHELTER_EMAIL("/email","Эл. почта приюта"),
    SHELTER_IN_MAP("/map", "Схема проезда"),
    SHELTER_OPENING_HOURS("/opening_hours", "Часы работы"),
    SHELTER_PHONE("/phone","Телефон приюта"),
    START("/start","Старт");

    private final String title;
    private final String description;

    Commands(String title, String description) {
        this.description = description;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
