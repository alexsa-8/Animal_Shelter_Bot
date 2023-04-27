package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Cat;
import pro.sky.animalshelterbot.exception.CatNotFoundException;
import pro.sky.animalshelterbot.repository.CatRepository;
import pro.sky.animalshelterbot.service.CatService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatServiceTests {

    @Mock
    private CatRepository repository;

    @InjectMocks
    private CatService service;

    Cat cat = new Cat(1L, "Name", 10, "Breed", PetStatus.FREE);

    @Test
    public void checkCreateCat() {
        when(repository.save(any(Cat.class))).thenReturn(cat);
        assertEquals(cat,service.createCat(cat, PetStatus.FREE));
    }

    @Test
    public void checkFindCat() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cat));
        when(repository.findById(2L)).thenThrow( new CatNotFoundException());
        assertEquals(cat, service.findCat(1L));
        assertThrows(CatNotFoundException.class, () -> service.findCat(2L));
    }

    @Test
    public void checkUpdateDog() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cat));
        when(repository.save(any(Cat.class))).thenReturn(cat);

        Cat cat1 = cat;
        cat1.setStatus(PetStatus.BUSY);

        assertEquals(cat1, service.updateCat(cat, PetStatus.BUSY));
    }

    @Test
    public void checkGetAllDog() {
        when(repository.findAll()).thenReturn(List.of(cat));
        assertEquals(List.of(cat), service.getAllCat());
    }

    @Test
    public void checkDeleteDog() {
        repository.save(cat);
        service.deleteCat(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }
}
