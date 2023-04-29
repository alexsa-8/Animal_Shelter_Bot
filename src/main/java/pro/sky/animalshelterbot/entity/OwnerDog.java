package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.OwnerStatus;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс OwnerDog, представляет сущность владельца питомца(собаки)
 * @author Marina Gubina
 */
@Entity
@Table(name = "owner_dog")
public class OwnerDog {

    /**
     * Поле: идентификационный номер владельца
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Поле: номер чата владельца
     */
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    /**
     * Поле: имя владельца
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Поле: номер телефона владельца
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * Поле: возраст владельца
     */
    @Column(name = "age", nullable = false)
    private int age;

    /**
     * Поле: статус владельца
     * @see OwnerStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OwnerStatus status;

    /**
     * Поле: имеющаяся собака
     *
     * @see Dog
     */
    @OneToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    /**
     * Поле: количество дней испытательного срока
     */
    @Column(name = "number_of_report_days" )
    private Long numberOfReportDays;

    public OwnerDog(Long chatId, String name, String phone, Long numberOfReportDays) {
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
        this.numberOfReportDays = numberOfReportDays;
    }

    public OwnerDog(Long chatId, String name, String phone) {
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
    }

    public OwnerDog() {
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OwnerStatus getStatus() {
        return status;
    }

    public void setStatus(OwnerStatus status) {
        this.status = status;
    }

    public Long getNumberOfReportDays() {
        return numberOfReportDays;
    }

    public void setNumberOfReportDays(Long numberOfReportDays) {
        this.numberOfReportDays = numberOfReportDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDog ownerDog = (OwnerDog) o;
        return age == ownerDog.age && Objects.equals(id, ownerDog.id) && Objects.equals(chatId, ownerDog.chatId)
                && Objects.equals(name, ownerDog.name) && Objects.equals(phone, ownerDog.phone)
                && status == ownerDog.status && Objects.equals(dog, ownerDog.dog)
                && Objects.equals(numberOfReportDays, ownerDog.numberOfReportDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phone, age, status, dog, numberOfReportDays);
    }

    @Override
    public String toString() {
        return "OwnerDog{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", status=" + status +
                ", dog=" + dog +
                ", numberOfReportDays=" + numberOfReportDays +
                '}';
    }
}