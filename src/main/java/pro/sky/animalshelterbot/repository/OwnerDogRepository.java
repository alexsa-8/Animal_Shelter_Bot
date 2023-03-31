package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.OwnerDog;

public interface OwnerDogRepository extends JpaRepository<OwnerDog, Long> {
}
