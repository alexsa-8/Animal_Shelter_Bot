package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.VolunteerStatus;

import javax.persistence.*;
import java.util.Objects;


/**
 * Класс Volunteer, представляет сущность волонтёра
 *
 * @author Rogozin Alexandr
 */

@Entity
@Table(name = "volunteer")
public class Volunteer {
    /**
     * Поле: идентификационный номер волонтёра
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

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
     *
     * @param id     идентификационный номер
     * @param name   имя
     * @param phone  телефон
     * @param status статус
     */
    public Volunteer(Long id, Long chatId, String name, String phone, VolunteerStatus status) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    /**
     * Конструктор без параметров для создания объекта "волонтёр"
     */

    public Volunteer() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() { return phone; }

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
        return Objects.equals(id, volunteer.id) && Objects.equals(chatId, volunteer.chatId) && Objects.equals(name, volunteer.name) && Objects.equals(phone, volunteer.phone) && status == volunteer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phone, status);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
