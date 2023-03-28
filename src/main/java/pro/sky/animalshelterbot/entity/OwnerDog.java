package pro.sky.animalshelterbot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class OwnerDog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String name;
    private String phone;
    private int age;

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
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                '}';
    }
}
