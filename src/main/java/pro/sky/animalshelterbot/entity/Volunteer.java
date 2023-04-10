package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.VolunteerStatus;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс Volunteer, представляет сущность волонтёра
 * @author Rogozin Alexandr
 */
//@Entity
@Table(name = "volunteer")
public class Volunteer {
    /**
     * Поле: идентификационный номер волонтёра
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Поле: имя волонтёра
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Поле: телефон волонтёра
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * Поле: статус волонтёра
     *
     * @see VolunteerStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VolunteerStatus status;

    /**
     * Конструктор с параметрами для создания объекта "волонтёр"
     * @param id     идентификационный номер
     * @param name   имя
     * @param phone  телефон
     * @param status статус
     */
    public Volunteer(Long id, String name, String phone, VolunteerStatus status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    /**
     * Конструктор без параметров для создания объекта "волонтёр"
     */
    public Volunteer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public VolunteerStatus getStatus() {
        return status;
    }

    public void setStatus(VolunteerStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(name, volunteer.name) && Objects.equals(phone, volunteer.phone) && status == volunteer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, status);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
