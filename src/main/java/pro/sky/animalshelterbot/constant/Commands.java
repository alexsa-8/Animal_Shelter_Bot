package pro.sky.animalshelterbot.constant;

/**
 * Enum Commands
 * Используется для констант команд для дальнейшей реализации кнопок меню
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 */
public enum Commands {
    NO("/no", "Отказ перед стартовым меню", generateCallbackData()),
    START_MENU("/start_menu", "Start menu", generateCallbackData()),
    CONTACT_DETAILS("/contact_details", "Оставить контактные данные", generateCallbackData()),
    ADVICES("/advices", "Советы", generateCallbackData()),
    LIST_DOCUMENTS("/list_documents", "Список документов", generateCallbackData()),
    DATING_RULES("/dating_rules", "Правила знакомства", generateCallbackData()),
    REPORT_FORM("/report_form", "Отправить отчет", generateCallbackData()),
    CALL_VOLUNTEER("/volunteer", "Позвать волонтера", generateCallbackData()),
    INFO("/info", "Информация о приюте", generateCallbackData()),
    ANIMAL_INFO("/animal_info", "Информация как взять питомца", generateCallbackData()),
    SUBMIT_REPORT("/submit_report", "Прислать отчет", generateCallbackData()),
    RECOMMENDATIONS("/recommendations", "Рекомендации", generateCallbackData()),
    SHELTER_ADDRESS("/address", "Адрес приюта", generateCallbackData()),
    SHELTER_EMAIL("/email", "Эл. почта приюта", generateCallbackData()),
    SHELTER_IN_MAP("/map", "Схема проезда", generateCallbackData()),
    SHELTER_OPENING_HOURS("/opening_hours", "Часы работы", generateCallbackData()),
    SHELTER_PHONE("/phone", "Телефон приюта", generateCallbackData()),
    START("/start", "Старт", generateCallbackData()),
    BACK("/back", "Назад", generateCallbackData()),
    SHELTER_RECOMMENDATIONS("/shelter_recommendations",
            "Рекомендации по технике безопасности в приюте", generateCallbackData()),
    SHELTER_DATA("/shelter_data", "Данные о приюте", generateCallbackData()),
    BACK_TO_ANIMAL_MENU("/back_animal_menu", "Назад", generateCallbackData()),
    RECOMMENDATIONS_TRANSPORTATION("/recommendations_transportation", "Рекомендации по траспортировке",
            generateCallbackData()),
    RECOMMENDATIONS_DOG("/recommendations_dog", "Рекомендации по обустройству собаки",
            generateCallbackData()),
    RECOMMENDATIONS_PUPPY("/recommendations_puppy", "Рекомендации по обустройству щенка",
            generateCallbackData()),
    RECOMMENDATIONS_DISABLED_DOG("/recommendations_disabled_dog",
            "Рекомендации по обустройству собаки с ограниченными возможностями", generateCallbackData()),
    ADVICES_CYNOLOGISTS("/advices_cynologists", "Советы кинологов", generateCallbackData()),
    LIST_CYNOLOGISTS("/list_cynologists", "Список проверенных кинологов", generateCallbackData()),
    REASONS_REFUSAL("/reasons_refusal", "Причины отказа в усыновлении", generateCallbackData());


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
     * Поле "Счетчик вызовов метода generateCallbackData"
     */
    private static int count;

    /**
     * Конструктор создания команды
     *
     * @param title        заголовок команды
     * @param description  описание команды
     * @param callbackData данные обратного вызова нажатия кнопки
     */
    Commands(String title, String description, String callbackData) {
        this.title = title;
        this.description = description;
        this.callbackData = callbackData;
    }

    /**
     * Получение данных обратного вызова нажатия кнопки
     *
     * @return данные обратного вызова нажатия кнопки
     */
    public String getCallbackData() {
        return callbackData;
    }

    /**
     * Получение заголовка команды
     *
     * @return заголовок команды
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получение описание команды
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Генерация строки callbackData
     *
     * @return строка CallbackData
     */
    public static String generateCallbackData() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append(" ".repeat(Math.max(0, count + 1)));
        count += 1;
        return sb.toString();
    }

}