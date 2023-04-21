package pro.sky.animalshelterbot.constant;

/**
 * Enum Commands
 * Используется для констант команд для дальнейшей реализации кнопок меню
 *
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 */
public enum Commands {
    TAKE_A_KITTEN("/take_a_kitten", "Start menu", generateCallbackData()),
    TAKE_THE_DOG("/take_the_dog", "Start menu", generateCallbackData()),
    CONTACT_DETAILS("/contact_details", "\uD83D\uDCDE Оставить контактные данные", generateCallbackData()),
    ADVICES("/advices", "\uD83D\uDDE3 Советы", generateCallbackData()),
    LIST_DOCUMENTS("/list_documents", "\uD83D\uDCCB Список документов", generateCallbackData()),
    DATING_RULES("/dating_rules", "\uD83E\uDD1D Правила знакомства", generateCallbackData()),
    REPORT_FORM("/report_form", "\uD83D\uDD16 Отправить отчет", generateCallbackData()),
    VOLUNTEER("/volunteer", "\uD83C\uDD98 Позвать волонтера", generateCallbackData()),
    INFO("/info", "ℹ️ Информация о приюте", generateCallbackData()),
    ANIMAL_INFO("/animal_info", "ℹ️ Информация как взять питомца", generateCallbackData()),
    SUBMIT_REPORT("/submit_report", "\uD83D\uDCDD Прислать отчет", generateCallbackData()),
    RECOMMENDATIONS("/recommendations", "\uD83E\uDDD0 Рекомендации", generateCallbackData()),
    SHELTER_ADDRESS("/address", "\uD83D\uDDFA Адрес приюта", generateCallbackData()),
    SHELTER_EMAIL("/email", "\uD83D\uDCE8 Эл. почта приюта", generateCallbackData()),
    SHELTER_IN_MAP("/map", "\uD83D\uDEA6 Схема проезда", generateCallbackData()),
    SHELTER_OPENING_HOURS("/opening_hours", "\uD83D\uDD50 Часы работы", generateCallbackData()),
    SHELTER_PHONE("/phone", "☎️ Телефон приюта", generateCallbackData()),
    START("/start", "Старт", generateCallbackData()),
    BACK("/back", "Назад", generateCallbackData()),
    SHELTER_RECOMMENDATIONS("/shelter_recommendations",
            "ℹ️ Рекомендации по технике безопасности в приюте", generateCallbackData()),
    SHELTER_DATA("/shelter_data", "ℹ️ Данные о приюте", generateCallbackData()),
    BACK_TO_ANIMAL_MENU("/back_animal_menu", "◀️ Назад", generateCallbackData()),
    RECOMMENDATIONS_TRANSPORTATION("/recommendations_transportation", "ℹ️ По траспортировке",
            generateCallbackData()),
    RECOMMENDATIONS_DOG("/recommendations_dog", "ℹ️ По обустройству для взрослого питомца",
            generateCallbackData()),
    RECOMMENDATIONS_PUPPY("/recommendations_puppy", "ℹ️ По обустройству для молодого питомца",
            generateCallbackData()),
    RECOMMENDATIONS_DISABLED_DOG("/recommendations_disabled_dog",
            "ℹ️ По обустройству для питомца с ограниченными возможностями", generateCallbackData()),
    ADVICES_CYNOLOGISTS("/advices_cynologists", "ℹ️ Советы кинологов", generateCallbackData()),
    LIST_CYNOLOGISTS("/list_cynologists", "\uD83D\uDCCB Список проверенных кинологов", generateCallbackData()),
    REASONS_REFUSAL("/reasons_refusal", "❌ Причины отказа в усыновлении", generateCallbackData());


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