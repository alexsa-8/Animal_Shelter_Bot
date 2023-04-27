package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.PetStatus;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс Cat, представляет сущность кошки
 *
 * @author Gubina Marina
 */
@Entity
@Table(name = "cat")
public class Cat {

    /**
     * Поле: идентификационный номер кошки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Поле: имя кошки
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Поле: возраст кошки
     */
    @Column(name = "age", nullable = false)
    private Integer age;

    /**
     * Поле: порода кошки
     */
    @Column(name = "breed", nullable = false)
    private String breed;

    /**
     * Поле: статус кошки
     *
     * @see PetStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PetStatus status;


    /**
     * Конструктор с параметрами для создания объекта "кошки"
     *
     * @param id     идентификационный номер
     * @param name   имя
     * @param age    возраст
     * @param breed  порода
     * @param status статус
     */
    public Cat(Long id, String name, Integer age, String breed, PetStatus status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.status = status;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id) && Objects.equals(name, cat.name) && Objects.equals(age, cat.age) && Objects.equals(breed, cat.breed) && status == cat.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, breed, status);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", breed='" + breed + '\'' +
                ", status=" + status +
                '}';
    }
}
