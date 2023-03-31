package pro.sky.animalshelterbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.exception.DogNotFoundException;
import pro.sky.animalshelterbot.exception.OwnerDogNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;

import java.util.Collection;

@Service
public class OwnerDogService {
    @Autowired
    private OwnerDogRepository repository;

    private final static Logger log = LoggerFactory.getLogger(OwnerDog.class);

    public OwnerDog create(OwnerDog ownerDog, OwnerStatus status) {
        log.info("Request to create owner dog {}", ownerDog);
        ownerDog.setStatus(status);
        return repository.save(ownerDog);
    }

    public OwnerDog find(Long id) {
        log.info("Request to getting owner dog  by id {}", id);
        return repository.findById(id).orElseThrow(OwnerDogNotFoundException::new);
    }
    public OwnerDog update(OwnerDog ownerDog) {
        log.info("Request to update owner dog  {}", ownerDog);
        if (ownerDog.getId() != null) {
            if (find(ownerDog.getId()) != null) {
                return repository.save(ownerDog);
            }
        }
        log.error("Request owner dog is not found");
        throw new DogNotFoundException();
    }

    public Collection<OwnerDog> getAll() {
        log.info("Request to getting all owner dog");
        return repository.findAll();
    }

    public void delete(Long id) {
        log.info("Request to delete owner dog by id {}", id);
        repository.deleteById(id);
    }
}
