package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {

}
