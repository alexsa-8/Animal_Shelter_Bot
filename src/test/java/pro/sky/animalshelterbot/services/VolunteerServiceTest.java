package pro.sky.animalshelterbot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalshelterbot.constant.VolunteerStatus;
import pro.sky.animalshelterbot.entity.Volunteer;
import pro.sky.animalshelterbot.repository.VolunteerRepository;
import pro.sky.animalshelterbot.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {
    @Mock
    private VolunteerRepository repository;

    @InjectMocks
    private VolunteerService service;

    private Volunteer volunteer1;
    private Volunteer volunteer2;
    private VolunteerStatus status1;
    private VolunteerStatus status2;


    @BeforeEach
    public void setUp() {
        volunteer1 = new Volunteer(1L, 1L, "name",
                "+79001112233", VolunteerStatus.ON_LINE);
        volunteer2 = new Volunteer(2L, 2L, "other name",
                "+79002223344", VolunteerStatus.ON_LINE);
    }

    @Test
    public void create() {
        when(repository.save(volunteer1)).thenReturn(volunteer1);

        Volunteer expected = volunteer1;
        expected.setStatus(VolunteerStatus.ON_LINE);
        assertEquals(expected,service.create(volunteer1,status1));
    }

    @Test
    void find() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(volunteer1));
        assertEquals(volunteer1, service.find(1L));
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(volunteer1));
        when(repository.save(volunteer1)).thenReturn(volunteer1);

        volunteer1.setId(1L);
        Volunteer expected = volunteer1;
        expected.setStatus(VolunteerStatus.ON_LINE);
        assertEquals(expected, service.update(volunteer1, VolunteerStatus.ON_LINE));
    }

    @Test
    void getAll() {
        List<Volunteer> volunteers = new ArrayList<>();
        volunteers.add(volunteer1);
        volunteers.add(volunteer2);

        when(repository.findAll()).thenReturn(volunteers);
        assertEquals(volunteers, service.getAll());
    }

    @Test
    void delete() {
        service.delete(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }
}