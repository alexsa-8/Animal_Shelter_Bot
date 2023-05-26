package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.OwnerDog;

/**
 * Интерфейс OwnerDogRepository
 * Интерфейс используется для работы с БД (для владельцев собак)
 * @author Kilikova Anna
 */
public interface OwnerDogRepository extends JpaRepository<OwnerDog, Long> {
    OwnerDog findByChatId(Long chatId);
}
