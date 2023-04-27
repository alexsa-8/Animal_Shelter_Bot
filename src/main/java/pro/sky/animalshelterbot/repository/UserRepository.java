package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.User;

/**
 * Интерфейс UserRepository
 * Интерфейс используется для работы с БД (для пользователей)
 * @author Bogomolov Ilya
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByChatId(Long chatId);
}
