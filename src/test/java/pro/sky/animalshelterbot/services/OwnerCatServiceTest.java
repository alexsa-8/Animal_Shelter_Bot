package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.entity.OwnerCat;

import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.exception.OwnerCatNotFoundException;
import pro.sky.animalshelterbot.exception.OwnerDogNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;
import pro.sky.animalshelterbot.service.OwnerCatService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerCatServiceTest {
    @Mock
    private OwnerCatRepository repository;

    @InjectMocks
    private OwnerCatService service;

    private OwnerCat ownerCat;
    private OwnerCat ownerCat2;
    @BeforeEach
    void setUp() {
        ownerCat = new OwnerCat(1L, 5544114L, "Tolya", "+79201144411", 18,
                OwnerStatus.IN_SEARCH, 14L);
        ownerCat2 = new OwnerCat(2L, 4444114L, "Bob", "+7978221444", 28,
                OwnerStatus.IN_SEARCH, 30L);

    }

    @Test
    void createOwnerCatTest() {
        when(repository.save(ownerCat)).thenReturn(ownerCat);
        assertEquals(ownerCat,service.create(ownerCat, OwnerStatus.IN_SEARCH));

    }


    @Test
    void updateOwnerCatTest() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(ownerCat));
        when(repository.save(any(OwnerCat.class))).thenReturn(ownerCat);
        ownerCat.setId(1L);
        OwnerCat expected = ownerCat;
        assertEquals(expected,service.update(ownerCat));
    }
    @Test
    public void shouldGetExceptionWhenUpdate(){
        ownerCat.setId(null);
        assertThrows(OwnerCatNotFoundException.class,
                () -> service.update(ownerCat));
    }

    @Test
    public void shouldGetExceptionsWhenNotFound(){
        when(repository.findById(anyLong())).thenThrow(new OwnerCatNotFoundException());
        assertThrows(OwnerCatNotFoundException.class, () -> service.find(anyLong()));
    }
    @Test
    void update2OwnerCatTest() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(ownerCat));
        when(repository.save(any(OwnerCat.class))).thenReturn(ownerCat);
        ownerCat.setStatus(OwnerStatus.APPROVED);
        OwnerCat expected = ownerCat;
        assertEquals(expected, service.updateStatus(ownerCat.getId(), OwnerStatus.APPROVED));
    }
    @Test
    void findOwnerCatTest() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(ownerCat));
        assertEquals(ownerCat,service.find(1L));
    }

    @Test
    void findOwnerCatByChatIdTest() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(ownerCat));
        assertEquals(ownerCat,service.findByChatId(5544114L));
    }

    @Test
    void getAllTest() {
        ArrayList<OwnerCat> list = new ArrayList<>();
        list.add(ownerCat);
        list.add(ownerCat2);

        when(repository.findAll()).thenReturn(list);
        Collection<OwnerCat> actual = service.getAll();

        assertThat(actual.size()).isEqualTo(list.size());
        assertThat(actual).isEqualTo(list);
    }

    @Test
    void deleteCatTest() {
        service.delete(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }





}
