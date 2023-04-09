package pro.sky.animalshelterbot.constant;

/**
 * Enum ReportStatus
 * Используется для обозначения статуса отчетов
 * @author Gubina Marina
 */
public enum ReportStatus{
    REPORT_POSTED("Отчет отправлен"),
    REPORT_ACCEPTED("Отчет одобрен"),
    REPORT_REJECTED("Отчет отклонен");

    /**
     * Поле "Описание"
     */
    private final String description;

    /**
     * Конструктор создания статуса отчета
     * @param description описание статуса
     */
    ReportStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
