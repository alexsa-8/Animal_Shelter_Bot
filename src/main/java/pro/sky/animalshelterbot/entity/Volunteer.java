package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.VolunteerStatus;

import java.util.Objects;


public class Volunteer {
    private String name;
    private String phone;
    private VolunteerStatus status;

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
