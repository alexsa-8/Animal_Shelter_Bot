package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.PetStatus;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс Dog, представляет сущность собаки
 * @author Kilikova Anna
 */
@Entity
@Table(name = "dog")
public class Dog {

    /**
     * Поле: идентификационный номер собаки
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Поле: имя собаки
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Поле: возраст собаки
     */
    @Column(name = "age", nullable = false)
    private Integer age;

    /**
     * Поле: порода собаки
     */
    @Column(name = "breed", nullable = false)
    private String breed;

    /**
     * Поле: статус собаки
     * @see PetStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PetStatus status;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    /**
     * Конструктор с параметрами для создания объекта "собака"
     * @param id идентификационный номер
     * @param name имя
     * @param age возраст
     * @param breed порода
     * @param status статус
     */
    public Dog(Long id, String name, Integer age, String breed, PetStatus status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.status = status;
    }

    /**
     * Конструктор без параметров для создания объекта "собака"
     */
    public Dog() {
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
        Dog dog = (Dog) o;
        return Objects.equals(id, dog.id) && Objects.equals(name, dog.name) && Objects.equals(age, dog.age) && Objects.equals(breed, dog.breed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, breed);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", breed='" + breed + '\'' +
                '}';
    }

}
