package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;
import pro.sky.animalshelterbot.service.OwnerDogService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerDogServiceTest {
    @Mock
    private OwnerDogRepository repository;

    @InjectMocks
    private OwnerDogService service;

    private OwnerDog ownerDog;
    private OwnerDog ownerDog1;
    @BeforeEach
    void setUp() {
        ownerDog = new OwnerDog(1L, 5544114L, "Tolya", "+79201144411", 18,
                OwnerStatus.IN_SEARCH, 14L);
        ownerDog1 = new OwnerDog(2L, 4444114L, "Bob", "+7978221444", 28,
                OwnerStatus.IN_SEARCH, 30L);

    }

    @Test
    void createOwnerDogTest() {
        when(repository.save(ownerDog)).thenReturn(ownerDog);
        assertEquals(ownerDog,service.create(ownerDog, OwnerStatus.IN_SEARCH));

    }


    @Test
    void updateOwnerDogTest() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(ownerDog));
        when(repository.save(any(OwnerDog.class))).thenReturn(ownerDog);
        ownerDog.setName("Roma");
        OwnerDog expected = ownerDog;
        assertEquals(expected, service.update(ownerDog));
    }
    @Test
    void update2OwnerDogTest() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(ownerDog));
        when(repository.save(any(OwnerDog.class))).thenReturn(ownerDog);
        ownerDog.setStatus(OwnerStatus.APPROVED);
        OwnerDog expected = ownerDog;
        assertEquals(expected, service.updateStatus(ownerDog.getId(), OwnerStatus.APPROVED));
    }

    @Test
    void findOwnerDogTest() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(ownerDog));
        assertEquals(ownerDog,service.find(1L));
    }

    @Test
    void findOwnerDogByChatIdTest() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(ownerDog));
        assertEquals(ownerDog, service.findByChatId(5544114L));
    }

    @Test
    void getAllTest() {
        ArrayList<OwnerDog> list = new ArrayList<>();
        list.add(ownerDog);
        list.add(ownerDog1);

        when(repository.findAll()).thenReturn(list);
        Collection<OwnerDog> actual = service.getAll();

        assertThat(actual.size()).isEqualTo(list.size());
        assertThat(actual).isEqualTo(list);
    }

    @Test
    void deleteDogTest() {
        service.delete(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

}
