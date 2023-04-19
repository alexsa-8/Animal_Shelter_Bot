package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.Report;
import pro.sky.animalshelterbot.exception.ReportNotFoundException;
import pro.sky.animalshelterbot.repository.ReportRepository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Сервис  ReportService
 * Сервис используется для создания, редактирования, удаления и получения отчетов из БД
 * @author Gubina Marina
 */
@Service
public class ReportService {

    private final ReportRepository repository;

    private final static Logger log = LoggerFactory.getLogger(DogService.class);

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание отчета и сохранение его в БД
     * Используется метод репозитория {@link ReportRepository#save(Object)}
     * @param report создается объект отчет
     * @return созданный отчет
     */
    public Report createReport(Report report){
        log.info("Request to create report {}",report);
        return repository.save(report);
    }

    public Report downloadReport(Long chatId, String animalDiet, String generalInfo,
                                 String changeBehavior, byte[] photo, LocalDate date){
        log.info("Request to download report");
        Report report = new Report();
        report.setChatId(chatId);
        report.setAnimalDiet(animalDiet);
        report.setGeneralInfo(generalInfo);
        report.setChangeBehavior(changeBehavior);
        report.setPhoto(photo);
        report.setDateMessage(date);
        report.setReportStatus(ReportStatus.REPORT_POSTED);

        return repository.save(report);
    }

    /**
     * Поиск отчета  в БД по id
     * Используется метод репозитория {@link ReportRepository#findById(Object)}
     * @param id идентификатор отчета
     * @throws ReportNotFoundException, если отчет с указанным id не найден в БД
     * @return искомый отчет
     */
    public Report findReport(Long id){
        log.info("Request to get report by id {}",id);
        return repository.findById(id).orElseThrow(ReportNotFoundException:: new);
    }

    /**
     * Изменение данных отчета в БД
     * Используется метод репозитория {@link ReportRepository#save(Object)}
     * @param report отчет
     * @param status статус отчета
     * @throws ReportNotFoundException, если отчет не найден в БД
     * @return измененный отчет
     */
    public Report updateReport(Report report, ReportStatus status){
        log.info("Request to update report {}",report);
        if(report.getId() != null){
            if(findReport(report.getId()) != null){
                report.setReportStatus(status);
            }
        }
        else{
            log.error("Request report is not found");
            throw new ReportNotFoundException();}
        return report;
    }

    /**
     * Удаление отчета из БД по id
     * Используется метод репозитория {@link ReportRepository#delete(Object)}
     * @param id идентификатор отчета
     */
    public void deleteReport(Long id){
        log.info("Request to delete report by id {}",id);
        repository.deleteById(id);
    }

    /**
     * Получение списка отчетов из БД
     * Используется метод репозитория {@link  ReportRepository#findAll()}
     * @return список отчетов
     */
    public Collection<Report> getAllReport(){
        log.info("Request to get all reports");
        return repository.findAll();
    }

    public Collection<Report> findNewReports() {
        return repository.findReports();
    }

    public Collection<Report> findOldReports() {
        return repository.findFirstReports();
    }

}
