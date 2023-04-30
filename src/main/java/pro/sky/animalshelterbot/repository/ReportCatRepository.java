package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.animalshelterbot.entity.ReportCat;

import java.util.Collection;

/**
 * Интерфейс ReportCatRepository
 * Интерфейс используется для работы с БД (для отчетов по котам)
 * @author Gubina Marina
 */
public interface ReportCatRepository extends JpaRepository<ReportCat, Long> {
    @Query(value="select distinct  on (owner_cat_id) * from report_cat order by owner_cat_id, date_message desc ", nativeQuery = true)
    Collection<ReportCat> findReports();

    @Query(value="select distinct  on (owner_cat_id) * from report_cat order by owner_cat_id, date_message desc ", nativeQuery = true)
    Collection<ReportCat>findFirstReports();

}
