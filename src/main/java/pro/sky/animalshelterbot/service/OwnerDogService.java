package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.exception.DogNotFoundException;
import pro.sky.animalshelterbot.exception.OwnerDogNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;

import java.util.Collection;

/**
 * Сервис OwnerDogService
 * Сервис используется для создания, редактирования, удаления и получения владельца собаки/списка владельцев собак из БД
 * @author Kilikova Anna
 */

@Service
public class OwnerDogService {
    private final OwnerDogRepository repository;

    private final static Logger log = LoggerFactory.getLogger(OwnerDog.class);

    public OwnerDogService(OwnerDogRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание владельца собаки и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#save(Object)}
     * @param ownerDog создается объект владелец собаки
     * @param status устанавливается статус владельца
     * @return созданный владелец
     */
    public OwnerDog create(OwnerDog ownerDog, OwnerStatus status) {
        log.info("Request to create owner dog {}", ownerDog);
        ownerDog.setStatus(status);
        return repository.save(ownerDog);
    }

    /**
     * Поиск владельца собаки в БД по id
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#findById(Object)}
     * @param id идентификатор владельца
     * @throws OwnerDogNotFoundException, если владелец с указанным id не найден
     * @return найденный владелец
     */
    public OwnerDog find(Long id) {
        log.info("Request to getting owner dog  by id {}", id);
        return repository.findById(id).orElseThrow(OwnerDogNotFoundException::new);
    }

    /**
     * Изменение данных владельца собаки в БД
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#save(Object)}
     * @param ownerDog изменяемый владелец
     * @param status статус владельца (изменить или оставить прежним)
     * @throws OwnerDogNotFoundException, если указанный владелец собаки не найден
     * @return измененный владелец собаки
     */
    public OwnerDog update(OwnerDog ownerDog, OwnerStatus status) {
        log.info("Request to update owner dog  {}", ownerDog);
        if (ownerDog.getId() != null) {
            if (find(ownerDog.getId()) != null) {
                ownerDog.setStatus(status);
                return repository.save(ownerDog);
            }
        }
        log.error("Request owner dog is not found");
        throw new DogNotFoundException();
    }

    /**
     * Получение списка владельцев собак из БД
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#findAll()}
     * @return список владельцев собак
     */
    public Collection<OwnerDog> getAll() {
        log.info("Request to getting all owner dog");
        return repository.findAll();
    }

    /**
     * Удаление владельца собаки из БД по id
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#delete(Object)}
     * @param id идентификатор владельца собаки
     */
    public void delete(Long id) {
        log.info("Request to delete owner dog by id {}", id);
        repository.deleteById(id);
    }
}
