package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.animalshelterbot.entity.ReportDog;

import java.util.Collection;

/**
 * Интерфейс ReportDogRepository
 * Интерфейс используется для работы с БД (для отчетов по собакам)
 * @author Gubina Marina
 */
public interface ReportDogRepository extends JpaRepository<ReportDog, Long> {
    @Query(value="select distinct  on (owner_dog_id) * from report_dog order by owner_dog_id, date_message desc ", nativeQuery = true)
    Collection<ReportDog> findReports();
    @Query(value="select distinct  on (owner_dog_id) * from report_dog order by owner_dog_id, date_message desc ", nativeQuery = true)
    Collection<ReportDog>findFirstReports();
}
