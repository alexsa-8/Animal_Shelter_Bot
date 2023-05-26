package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.ReportCat;
import pro.sky.animalshelterbot.exception.ReportNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.repository.ReportCatRepository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Сервис  ReportCatService
 * Сервис используется для создания, редактирования, удаления
 * и получения отчетов по котам из БД
 * @author Gubina Marina
 */
@Service
public class ReportCatService {

    private final ReportCatRepository repository;
    private final OwnerCatRepository ownerCatRepository;

    private final static Logger log = LoggerFactory.getLogger(ReportCatService.class);

    public ReportCatService(ReportCatRepository repository, OwnerCatRepository ownerCatRepository) {
        this.repository = repository;
        this.ownerCatRepository = ownerCatRepository;
    }

    /**
     * Создание отчета и сохранение его в БД
     * Используется метод репозитория {@link ReportCatRepository#save(Object)}
     * @param reportCat создается объект отчет
     * @return созданный отчет
     */
    public ReportCat createReport(ReportCat reportCat){
        log.info("Request to create reportCat {}", reportCat);
        return repository.save(reportCat);
    }

    public ReportCat downloadReport(Long chatId, String animalDiet, String generalInfo,
                                    String changeBehavior, byte[] photo, LocalDate date){
        log.info("Request to download reportCat");
        ReportCat reportCat = new ReportCat();
        reportCat.setChatId(chatId);
        reportCat.setAnimalDiet(animalDiet);
        reportCat.setGeneralInfo(generalInfo);
        reportCat.setChangeBehavior(changeBehavior);
        reportCat.setPhoto(photo);
        reportCat.setDateMessage(date);
        reportCat.setReportStatus(ReportStatus.REPORT_POSTED);
        reportCat.setOwnerCat(ownerCatRepository.findByChatId(chatId));

        return repository.save(reportCat);
    }

    /**
     * Поиск отчета  в БД по id
     * Используется метод репозитория {@link ReportCatRepository#findById(Object)}
     * @param id идентификатор отчета
     * @throws ReportNotFoundException, если отчет с указанным id не найден в БД
     * @return искомый отчет
     */
    public ReportCat findReport(Long id){
        log.info("Request to get report by id {}",id);
        return repository.findById(id).orElseThrow(ReportNotFoundException:: new);
    }

    /**
     * Изменение данных отчета в БД
     * Используется метод репозитория {@link ReportCatRepository#save(Object)}
     * @param id id отчетa
     * @param status статус отчета
     * @throws ReportNotFoundException, если отчет не найден в БД
     * @return измененный отчет
     */
    public ReportCat updateReport(Long id, ReportStatus status){
        log.info("Request to update reportCat {}", id);
        if (id != null) {
            ReportCat reportCat = findReport(id);
            if (reportCat != null) {
                reportCat.setReportStatus(status);
                return repository.save(reportCat);
            }
        }
        log.error("Request report cat is not found");
        throw new ReportNotFoundException();
    }

    /**
     * Удаление отчета из БД по id
     * Используется метод репозитория {@link ReportCatRepository#delete(Object)}
     * @param id идентификатор отчета
     */
    public void deleteReport(Long id){
        log.info("Request to delete report by id {}",id);
        repository.deleteById(id);
    }

    /**
     * Получение списка отчетов из БД
     * Используется метод репозитория {@link  ReportCatRepository#findAll()}
     * @return список отчетов
     */
    public Collection<ReportCat> getAllReport(){
        log.info("Request to get all reports");
        return repository.findAll();
    }

    public Collection<ReportCat> findNewReports() {
        return repository.findReports();
    }

    public Collection<ReportCat> findOldReports() {
        return repository.findFirstReports();
    }

}
