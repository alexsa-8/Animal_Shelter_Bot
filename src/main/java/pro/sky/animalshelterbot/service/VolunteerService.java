package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.VolunteerStatus;
import pro.sky.animalshelterbot.entity.Volunteer;
import pro.sky.animalshelterbot.exception.VolunteerNotFoundException;
import pro.sky.animalshelterbot.repository.VolunteerRepository;

import java.util.Collection;

/**
 * Сервис VolunteerService
 * Сервис используется для создания, редактирования, удаления, получения волонтёра и списка волонтёров из БД,
 *
 * @author Rogozin Alexandr
 */
@Service
public class VolunteerService {
    private final VolunteerRepository repository;

    private final static Logger log = LoggerFactory.getLogger(VolunteerService.class);

    public VolunteerService(VolunteerRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание волонтёра и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#save(Object)}
     *
     * @param volunteer создается объект волонтёр
     * @param status    устанавливается статус волонтёр
     * @return созданный волонтёр
     */
    public Volunteer create(Volunteer volunteer, VolunteerStatus status) {
        log.info("Request to create a volunteer {}", volunteer);
        volunteer.setStatus(status);
        return repository.save(volunteer);
    }

    /**
     * Поиск волонтёра в БД по id
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#findById(Object)}
     *
     * @param id идентификатор волонтёра
     * @return найденный волонтёр
     * @throws VolunteerNotFoundException, если волонтёр с указанным id не найден
     */
    public Volunteer find(Long id) {
        log.info("Request to get a volunteer by id {}", id);
        return repository.findById(id).orElseThrow(VolunteerNotFoundException::new);
    }

    /**
     * Изменение данных волонтёра в БД
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#save(Object)}
     *
     * @param volunteer изменяемый волонтёр
     * @param status    статус волонтёра (изменить или оставить прежним)
     * @return измененный волонтёр
     * @throws VolunteerNotFoundException, если указанный волонтёр не найден
     */
    public Volunteer update(Volunteer volunteer, VolunteerStatus status) {
        log.info("Request to update the volunteer  {}", volunteer);
        if (volunteer.getId() != null) {
            if (find(volunteer.getId()) != null) {
                volunteer.setStatus(status);
                return repository.save(volunteer);
            }
        }
        log.error("The volunteer on the application was not found");
        throw new VolunteerNotFoundException();
    }

    /**
     * Получение списка волонтёров из БД
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#findAll()}
     *
     * @return список волонтёров
     */
    public Collection<Volunteer> getAll() {
        log.info("Request to receive all volunteers");
        return repository.findAll();
    }

    /**
     * Удаление волонтёра из БД по id
     * <br>
     * Используется метод репозитория {@link VolunteerRepository#delete(Object)}
     *
     * @param id идентификатор волонтёра
     */
    public void delete(Long id) {
        log.info("Request to delete a volunteer by id {}", id);
        repository.deleteById(id);
    }
}
