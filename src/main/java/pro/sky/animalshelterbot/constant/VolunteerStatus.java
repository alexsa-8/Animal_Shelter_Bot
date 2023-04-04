package pro.sky.animalshelterbot.constant;

public enum VolunteerStatus {
    WRONG_REPORT("Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного");
    private final String description;

    VolunteerStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
