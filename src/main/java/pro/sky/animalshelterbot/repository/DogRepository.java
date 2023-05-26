package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.Dog;

/**
 * Интерфейс DogRepository
 * Интерфейс используется для работы с БД (для собак)
 * @author Kilikova Anna
 */
public interface DogRepository extends JpaRepository<Dog, Long> {

}
