package pro.sky.animalshelterbot.constant;

/**
 * Enum Commands
 * Используется для констант команд для дальнейшей реализации кнопок меню
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 */
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

    /**
     * Поле "Заголовок"
     */
    private final String title;

    /**
     * Поле "Описание"
     */
    private final String description;

    /**
     * Поле "Данные обратного вызова нажатия кнопки"
     */
    private final String callbackData;

    /**
     * Конструктор создания команды
     * @param title заголовок команды
     * @param description описание команды
     * @param callbackData данные обратного вызова нажатия кнопки
     */
    Commands(String title, String description, String callbackData) {
        this.description = description;
        this.title = title;
        this.callbackData = callbackData;
    }

    /**
     * Получение данных обратного вызова нажатия кнопки
     * @return данные обратного вызова нажатия кнопки
     */
    public String getCallbackData() {
        return callbackData;
    }

    /**
     * Получение заголовка команды
     * @return заголовок команды
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получение описание команды
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }
}
