package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.exception.DogNotFoundException;
import pro.sky.animalshelterbot.repository.DogRepository;

import java.util.Collection;

/**
 * Сервис DogService
 * Сервис используется для создания, редактирования, удаления и получения собаки/списка собак из БД
 * @author Kilikova Anna
 */

@Service
public class DogService {

    @Autowired
    private DogRepository repository;

    private final static Logger log = LoggerFactory.getLogger(DogService.class);

    /**
     * Создание собаки и сохранение ее в БД
     * <br>
     * Используется метод репозитория {@link DogRepository#save(Object)}
     * @param dog создается объект собака
     * @param status устанавливается статус животного в приюте
     * @return созданная собака
     */
    public Dog createDog(Dog dog, PetStatus status) {
        log.info("Request to create dog {}", dog);
        dog.setStatus(status);
        return repository.save(dog);
    }

    /**
     * Поиск собаки в БД по id
     * <br>
     * Используется метод репозитория {@link DogRepository#findById(Object)}
     * @param id идентификатор собаки
     * @throws DogNotFoundException, если собака с указанным id не найденна в БД
     * @return найденная собака
     */
    public Dog findDog(Long id) {
        log.info("Request to getting dog by id {}", id);
        return repository.findById(id).orElseThrow(DogNotFoundException::new);
    }

    /**
     * Изменение данных собаки в БД
     * <br>
     * Используется метод репозитория {@link DogRepository#save(Object)}
     * @param dog изменяемая собака
     * @param status статус собаки (оставить прежним или изменить)
     * @throws DogNotFoundException, если собака не найденна в БД
     * @return измененная собака
     */
    public Dog updateDog(Dog dog, PetStatus status) {
        log.info("Request to update dog {}", dog);
        if (dog.getId() != null) {
            if (findDog(dog.getId()) != null) {
                dog.setStatus(status);
                return repository.save(dog);
            }
        }
        log.error("Request dog is not found");
        throw new DogNotFoundException();
    }

    /**
     * Получение списка собак из БД
     * <br>
     * Используется метод репозитория {@link DogRepository#findAll()}
     * @return список собак
     */
    public Collection<Dog> getAllDog() {
        log.info("Request to getting all dog");
        return repository.findAll();
    }

    /**
     * Удаление собаки из БД по id
     * <br>
     * Используется метод репозитория {@link DogRepository#delete(Object)}
     * @param id идентификатор собаки
     */
    public void deleteDog(Long id) {
        log.info("Request to delete dog by id {}", id);
        repository.deleteById(id);
    }
}


