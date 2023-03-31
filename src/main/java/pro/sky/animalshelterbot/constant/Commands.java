package pro.sky.animalshelterbot.constant;

public enum Commands {
    CONTACT_DETAILS("/contact_details", "Оставить контактные данные", "  "),
    ADVICES("/advices", "Советы", "   "),
    LIST_DOCUMENTS("/list_documents", "Список документов", "    "),
    DATING_RULES("/dating_rules", "Правила знакомства", "     "),
    REPORT_FORM("/report_form", "Форма отчета","      "),
    CALL_VOLUNTEER("/volunteer","Позвать волонтера", "       "),
    INFO("/info", "Информация о приюте", "        "),
    ANIMAL_INFO("/animal_info", "Информация как взять питомца", "         "),
    SUBMIT_REPORT("/submit_report", "Прислать отчет", "          "),
    RECOMMENDATIONS("/recommendations","Рекомендации", "           "),
    SHELTER_ADDRESS("/address", "Адрес приюта", "            "),
    SHELTER_EMAIL("/email","Эл. почта приюта", "             "),
    SHELTER_IN_MAP("/map", "Схема проезда", "              "),
    SHELTER_OPENING_HOURS("/opening_hours", "Часы работы", "               "),
    SHELTER_PHONE("/phone","Телефон приюта", "                "),
    START("/start","Старт", "                 ");

    private final String title;
    private final String description;

    private final String callbackData;

    Commands(String title, String description, String callbackData) {
        this.description = description;
        this.title = title;
        this.callbackData = callbackData;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
