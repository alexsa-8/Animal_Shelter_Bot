package pro.sky.animalshelterbot.constant;

/**
 * Enum VolunteerStatus
 * Используется для констант статусов волонтёров
 * @author Rogozin Alexandr
 */
public enum VolunteerStatus {
    WRONG_REPORT("Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного");
    /**
     * Поле "Описание"
     */
    private final String description;

    /**
     * Конструктор создания статуса волонтёра
     * @param description описание статуса волонтёра
     */
    VolunteerStatus(String description) {
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
