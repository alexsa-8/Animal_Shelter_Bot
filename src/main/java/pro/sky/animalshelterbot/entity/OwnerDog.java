package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.OwnerStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "owner_dog")
public class OwnerDog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone",nullable = false)
    private String phone;
    @Column(name = "age", nullable = false)
    private int age;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OwnerStatus status;

    @OneToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDog ownerDog = (OwnerDog) o;
        return age == ownerDog.age && Objects.equals(id, ownerDog.id) && Objects.equals(chatId, ownerDog.chatId) && Objects.equals(name, ownerDog.name) && Objects.equals(phone, ownerDog.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phone, age);
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
                '}';
    }
}
