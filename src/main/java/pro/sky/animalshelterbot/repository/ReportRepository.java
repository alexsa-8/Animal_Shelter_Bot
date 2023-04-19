package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.animalshelterbot.entity.Report;

import java.util.Collection;

/**
 * Интерфейс ReportRepository
 * Интерфейс используется для работы с БД (для отчетов)
 * @author Gubina Marina
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value="select distinct  on (owner_dog_id) * from report order by owner_dog_id, date_message desc ", nativeQuery = true)
    Collection<Report> findReports();

    @Query(value="select distinct  on (owner_dog_id) * from report order by owner_dog_id, date_message ", nativeQuery = true)
    Collection<Report>findFirstReports();
}
