package pro.sky.animalshelterbot.service;

import liquibase.pro.packaged.D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.exception.DogNotFoundException;
import pro.sky.animalshelterbot.repository.DogRepository;

import java.util.Collection;

@Service
public class DogService {

    @Autowired
    private DogRepository repository;

    private final static Logger log = LoggerFactory.getLogger(DogService.class);

    public Dog createDog(Dog dog, PetStatus status) {
        log.info("Request to create dog {}", dog);
        dog.setStatus(status);
        return repository.save(dog);
    }

    public Dog findDog(Long id) {
        log.info("Request to getting dog by id {}", id);
        return repository.findById(id).orElseThrow(DogNotFoundException::new);
    }
    public Dog updateDog(Dog dog) {
        log.info("Request to update dog {}", dog);
        if (dog.getId() != null) {
            if (findDog(dog.getId()) != null) {
                return repository.save(dog);
            }
        }
        log.error("Request dog is not found");
        throw new DogNotFoundException();
    }

    public Collection<Dog> getAllDog() {
        log.info("Request to getting all dog");
        return repository.findAll();
    }

    public void deleteDog(Long id) {
        log.info("Request to delete dog by id {}", id);
        repository.deleteById(id);
    }
}


