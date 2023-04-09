package pro.sky.animalshelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalshelterbot.entity.Report;

/**
 * Интерфейс ReportRepository
 * Интерфейс используется для работы с БД (для отчетов)
 * @author Gubina Marina
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

}
