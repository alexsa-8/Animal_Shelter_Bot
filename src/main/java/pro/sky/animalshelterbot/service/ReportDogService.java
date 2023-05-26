package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.ReportStatus;
import pro.sky.animalshelterbot.entity.ReportDog;
import pro.sky.animalshelterbot.exception.ReportNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;
import pro.sky.animalshelterbot.repository.ReportDogRepository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Сервис  ReportDogService
 * Сервис используется для создания, редактирования, удаления
 * и получения отчетов по собакам из БД
 * @author Gubina Marina
 */
@Service
public class ReportDogService {

    private final ReportDogRepository repository;

    private final OwnerDogRepository ownerDogRepository;

    private final static Logger log = LoggerFactory.getLogger(ReportDogService.class);

    public ReportDogService(ReportDogRepository repository, OwnerDogRepository ownerDogRepository) {
        this.repository = repository;
        this.ownerDogRepository = ownerDogRepository;
    }

    /**
     * Создание отчета и сохранение его в БД
     * Используется метод репозитория {@link ReportDogRepository#save(Object)}
     * @param reportDog создается объект отчет
     * @return созданный отчет
     */
    public ReportDog createReport(ReportDog reportDog){
        log.info("Request to create reportDog {}", reportDog);
        return repository.save(reportDog);
    }

    public ReportDog downloadReport(Long chatId, String animalDiet, String generalInfo,
                                    String changeBehavior, byte[] photo, LocalDate date){
        log.info("Request to download reportDog");
        ReportDog reportDog = new ReportDog();
        reportDog.setChatId(chatId);
        reportDog.setAnimalDiet(animalDiet);
        reportDog.setGeneralInfo(generalInfo);
        reportDog.setChangeBehavior(changeBehavior);
        reportDog.setPhoto(photo);
        reportDog.setDateMessage(date);
        reportDog.setReportStatus(ReportStatus.REPORT_POSTED);
        reportDog.setOwnerDog(ownerDogRepository.findByChatId(chatId));

        return repository.save(reportDog);
    }

    /**
     * Поиск отчета  в БД по id
     * Используется метод репозитория {@link ReportDogRepository#findById(Object)}
     * @param id идентификатор отчета
     * @throws ReportNotFoundException, если отчет с указанным id не найден в БД
     * @return искомый отчет
     */
    public ReportDog findReport(Long id){
        log.info("Request to get report by id {}",id);
        return repository.findById(id).orElseThrow(ReportNotFoundException:: new);
    }

    /**
     * Изменение данных отчета в БД
     * Используется метод репозитория {@link ReportDogRepository#save(Object)}
     * @param id id отчета
     * @param status статус отчета
     * @throws ReportNotFoundException, если отчет не найден в БД
     * @return измененный отчет
     */
    public ReportDog updateReport(Long id, ReportStatus status){
        log.info("Request to update reportDog {}", id);
        if(id != null){
            ReportDog reportDog = findReport(id);
            if(reportDog != null){
                reportDog.setReportStatus(status);
                return repository.save(reportDog);
            }
        }
        log.error("Request reportDog is not found");
        throw new ReportNotFoundException();
    }

    /**
     * Удаление отчета из БД по id
     * Используется метод репозитория {@link ReportDogRepository#delete(Object)}
     * @param id идентификатор отчета
     */
    public void deleteReport(Long id){
        log.info("Request to delete report by id {}",id);
        repository.deleteById(id);
    }

    /**
     * Получение списка отчетов из БД
     * Используется метод репозитория {@link  ReportDogRepository#findAll()}
     * @return список отчетов
     */
    public Collection<ReportDog> getAllReport(){
        log.info("Request to get all reports");
        return repository.findAll();
    }

    public Collection<ReportDog> findNewReports() {
        return repository.findReports();
    }

    public Collection<ReportDog> findOldReports() {
        return repository.findFirstReports();
    }

}
