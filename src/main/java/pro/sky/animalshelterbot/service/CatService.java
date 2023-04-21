package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.exception.CatNotFoundException;
import pro.sky.animalshelterbot.repository.CatRepository;

import java.util.Collection;

/**
 * Сервис CatService
 * Сервис используется для создания, редактирования, удаления и получения кошки/списка кошек из БД
 * @author Gubina Marina
 */
@Service
public class CatService {

    private final CatRepository repository;

    private final static Logger log = LoggerFactory.getLogger(CatService.class);

    public CatService(CatRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание кота и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link CatRepository#save(Object)}
     * @param cat создается объект кот
     * @param status устанавливается статус животного в приюте
     * @return созданный кот
     */
    public Cat createCat(Cat cat, PetStatus status) {
        log.info("Request to create cat {}", cat);
        cat.setStatus(status);
        return repository.save(cat);
    }

    /**
     * Поиск кота в БД по id
     * <br>
     * Используется метод репозитория {@link CatRepository#findById(Object)}
     * @param id идентификатор кота
     * @throws CatNotFoundException, если кот с указанным id не найден в БД
     * @return найденный кот
     */
    public Cat findCat(Long id) {
        log.info("Request to getting cat by id {}", id);
        return repository.findById(id).orElseThrow(CatNotFoundException::new);
    }

    /**
     * Изменение данных кота в БД
     * <br>
     * Используется метод репозитория {@link CatRepository#save(Object)}
     * @param cat изменяемый кот
     * @param status статус кота (оставить прежним или изменить)
     * @throws CatNotFoundException, если кот не найденн в БД
     * @return измененный кот
     */
    public Cat updateCat(Cat cat, PetStatus status) {
        log.info("Request to update cat {}", cat);
        if (cat.getId() != null) {
            if (findCat(cat.getId()) != null) {
                cat.setStatus(status);
                return repository.save(cat);
            }
        }
        log.error("Request cat is not found");
        throw new CatNotFoundException();
    }

    /**
     * Получение списка котов из БД
     * <br>
     * Используется метод репозитория {@link CatRepository#findAll()}
     * @return список котов
     */
    public Collection<Cat> getAllCat() {
        log.info("Request to getting all cats");
        return repository.findAll();
    }

    /**
     * Удаление кота из БД по id
     * <br>
     * Используется метод репозитория {@link CatRepository#delete(Object)}
     * @param id идентификатор кота
     */
    public void deleteCat(Long id) {
        log.info("Request to delete cat by id {}", id);
        repository.deleteById(id);
    }
}
