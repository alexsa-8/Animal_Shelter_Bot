package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.exception.DogNotFoundException;
import pro.sky.animalshelterbot.repository.DogRepository;
import pro.sky.animalshelterbot.service.DogService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocServiceTests {

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogService dogService;

    private final Dog dog = new Dog(1L, "Name", 10, "Breed", PetStatus.FREE);

    @Test
    public void checkCreateDog() {
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);
        assertEquals(dog,dogService.createDog(dog, PetStatus.FREE));
    }

    @Test
    public void checkFindDog() {
        when(dogRepository.findById(1L)).thenReturn(Optional.ofNullable(dog));
        when(dogRepository.findById(2L)).thenThrow( new DogNotFoundException());
        assertEquals(dog, dogService.findDog(1L));
        assertThrows(DogNotFoundException.class, () -> dogService.findDog(2L));
    }

    @Test
    public void checkUpdateDog() {
        when(dogRepository.findById(1L)).thenReturn(Optional.ofNullable(dog));
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        dog.setId(1L);
        Dog dog2 = dog;
        dog2.setStatus(PetStatus.BUSY);

        assertEquals(dog2, dogService.updateDog(dog, PetStatus.BUSY));
    }

    @Test
    public void checkGetAllDog() {
        when(dogRepository.findAll()).thenReturn(List.of(dog));
        assertEquals(List.of(dog), dogService.getAllDog());
    }

    @Test
    public void checkDeleteDog() {
        dog.setId(1L);
        dogRepository.save(dog);
        dogService.deleteDog(1L);
        verify(dogRepository, Mockito.times(1)).deleteById(1L);
    }
}
