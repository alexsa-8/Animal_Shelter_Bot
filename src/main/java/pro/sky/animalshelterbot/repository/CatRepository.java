package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.Cat;

/**
 * Интерфейс CatRepository
 * Интерфейс используется для работы с БД (для кошек)
 * @author Gubina Marina
 */
public interface CatRepository extends JpaRepository<Cat,Long> {
}
