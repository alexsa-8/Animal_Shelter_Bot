package pro.sky.animalshelterbot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String mail;
    private LocalDateTime openingHours;
    private byte[] map;

}
