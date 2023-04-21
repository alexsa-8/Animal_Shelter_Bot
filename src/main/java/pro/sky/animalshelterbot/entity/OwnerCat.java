package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.OwnerStatus;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс OwnerCat, представляет сущность владельца питомца(кота)
 * @author Marina Gubina
 */
@Entity
@Table(name = "owner_cat")
public class OwnerCat {

    /**
     * Поле: идентификационный номер владельца кота
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Поле: номер чата владельца кота
     */
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    /**
     * Поле: имя владельца кота
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Поле: номер телефона владельца кота
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * Поле: возраст владельца кота
     */
    @Column(name = "age", nullable = false)
    private int age;

    /**
     * Поле: статус владельца кота
     * @see OwnerStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OwnerStatus status;

    /**
     * Поле: имеющийся кот
     * @see Cat
     */
    @OneToOne
    @JoinColumn(name = "cat_id")
    private Cat cat;

    /**
     * Поле: количество дней испытательного срока
     */
    @Column(name = "number_of_report_days" )
    private Long numberOfReportDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OwnerStatus getStatus() {
        return status;
    }

    public void setStatus(OwnerStatus status) {
        this.status = status;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
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
        OwnerCat ownerCat = (OwnerCat) o;
        return age == ownerCat.age && Objects.equals(id, ownerCat.id) && Objects.equals(chatId, ownerCat.chatId) && Objects.equals(name, ownerCat.name) && Objects.equals(phone, ownerCat.phone) && status == ownerCat.status && Objects.equals(cat, ownerCat.cat) && Objects.equals(numberOfReportDays, ownerCat.numberOfReportDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phone, age, status, cat, numberOfReportDays);
    }

    @Override
    public String toString() {
        return "OwnerCat{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", status=" + status +
                ", cat=" + cat +
                ", numberOfReportDays=" + numberOfReportDays +
                '}';
    }
}
