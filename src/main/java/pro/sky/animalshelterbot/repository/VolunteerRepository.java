package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.Volunteer;

/**
 * Интерфейс VolunteerRepository
 * Интерфейс используется для работы с БД (для волонтёров)
 * @author Rogozin Alexandr
 */
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
