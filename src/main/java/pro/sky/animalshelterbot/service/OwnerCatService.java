package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.exception.OwnerCatNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;

import java.util.Collection;

/**
 * Сервис OwnerCatService
 * Сервис используется для создания, редактирования, удаления и получения владельца кота/списка владельцев котов из БД
 * @author Gubina Marina
 */

@Service
public class OwnerCatService {
    private final OwnerCatRepository repository;

    private final static Logger log = LoggerFactory.getLogger(OwnerCat.class);

    public OwnerCatService(OwnerCatRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание владельца кота и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#save(Object)}
     * @param owner создается объект владелец кота
     * @param status устанавливается статус владельца
     * @return созданный владелец
     */
    public OwnerCat create(OwnerCat owner, OwnerStatus status) {
        log.info("Request to create owner cat {}", owner);
        owner.setStatus(status);
        return repository.save(owner);
    }

    /**
     * Поиск владельца кота в БД по id
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#findById(Object)}
     * @param id идентификатор владельца
     * @throws OwnerCatNotFoundException, если владелец с указанным id не найден
     * @return найденный владелец
     */
    public OwnerCat find(Long id) {
        log.info("Request to getting owner cat  by id {}", id);
        return repository.findById(id).orElseThrow(OwnerCatNotFoundException::new);
    }

    /**
     * Изменение данных владельца кота в БД
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#save(Object)}
     * @param owner изменяемый владелец
     * @param status статус владельца (изменить или оставить прежним)
     * @throws OwnerCatNotFoundException, если указанный владелец кота не найден
     * @return измененный владелец кота
     */
    public OwnerCat update(OwnerCat owner, OwnerStatus status) {
        log.info("Request to update owner cat  {}", owner);
        if (owner.getId() != null) {
            if (find(owner.getId()) != null) {
                owner.setStatus(status);
                return repository.save(owner);
            }
        }
        log.error("Request owner cat is not found");
        throw new OwnerCatNotFoundException();
    }

    /**
     * Получение списка владельцев котов из БД
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#findAll()}
     * @return список владельцев котов
     */
    public Collection<OwnerCat> getAll() {
        log.info("Request to getting all owner cat");
        return repository.findAll();
    }

    /**
     * Удаление владельца кота из БД по id
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#delete(Object)}
     * @param id идентификатор владельца кота
     */
    public void delete(Long id) {
        log.info("Request to delete owner cat by id {}", id);
        repository.deleteById(id);
    }

    /**
     * добавление количества дней исп.срока
     * @return измененные данные
     */
    public OwnerCat changeNumberOfReportDays(Long id, Long number) {
        OwnerCat owner = new OwnerCat();
        if (owner.getId() != null) {
            if (find(owner.getId()) != null) {
                owner.setNumberOfReportDays(owner.getNumberOfReportDays() + number);
                return repository.save(owner);
            }
        }
        log.error("Request owner cat is not found");
        throw new OwnerCatNotFoundException();
    }

}
