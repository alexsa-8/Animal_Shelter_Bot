package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.OwnerCat;

/**
 * Интерфейс OwnerCatRepository
 * Интерфейс используется для работы с БД (для владельцев котов)
 * @author Gubina Marina
 */
public interface OwnerCatRepository extends JpaRepository<OwnerCat,Long> {
    OwnerCat findByChatId(Long chatId);
}
