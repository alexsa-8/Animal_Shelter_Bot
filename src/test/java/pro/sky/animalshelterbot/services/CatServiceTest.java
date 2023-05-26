package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatServiceTest {

    @Mock
    private CatRepository repository;

    @InjectMocks
    private CatService service;

    private Cat cat1;
    private Cat cat2;

    @BeforeEach
    public void setUp(){
        cat1 = new Cat();
        cat1.setId(1L);
        cat1.setName("Vasya");
        cat1.setAge(1);
        cat1.setBreed("british");
        cat1.setStatus(PetStatus.BUSY);

        cat2 = new Cat();
        cat2.setId(2L);
        cat2.setName("Musya");
        cat2.setAge(5);
        cat2.setBreed("british");
        cat2.setStatus(PetStatus.FREE);
    }

    @Test
    public void shouldCreateCat(){
        when(repository.save(cat1)).thenReturn(cat1);
        assertEquals(cat1,service.createCat(cat1,PetStatus.BUSY));
    }

    @Test
    public void shouldFindCat(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cat1));
        assertEquals(cat1,service.findCat(1L));
    }

    @Test
    public void shouldGetExceptionsWhenNotFound(){
        when(repository.findById(anyLong())).thenThrow(new CatNotFoundException());
        assertThrows(CatNotFoundException.class, () -> service.findCat(anyLong()));
    }

    @Test
    public void shouldUpdateCat(){
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cat1));
        when(repository.save(cat1)).thenReturn(cat1);

        cat1.setId(1L);
        Cat expected = cat1;
        expected.setStatus(PetStatus.FREE);
        assertEquals(expected,service.updateCat(cat1,PetStatus.FREE));
    }

    @Test
    public void shouldGetExceptionWhenUpdate(){
        cat1.setId(null);
        assertThrows(CatNotFoundException.class,
                () -> service.updateCat(cat1,PetStatus.FREE));
    }

    @Test
    public void shouldDeleteCat(){
        service.deleteCat(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void shouldGetAllReports(){
        Collection<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);

        when(repository.findAll()).thenReturn((List<Cat>) cats);
        assertEquals(cats,service.getAllCat());
    }
}
