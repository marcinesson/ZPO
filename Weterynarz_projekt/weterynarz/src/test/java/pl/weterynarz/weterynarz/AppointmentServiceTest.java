package pl.weterynarz.weterynarz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.weterynarz.weterynarz.model.AppUser;
import pl.weterynarz.weterynarz.model.Appointment;
import pl.weterynarz.weterynarz.model.UserRole;
import pl.weterynarz.weterynarz.repository.AppUserRepository;
import pl.weterynarz.weterynarz.repository.AppointmentRepository;
import pl.weterynarz.weterynarz.service.AppUserService;
import pl.weterynarz.weterynarz.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppUserService appUserService;

    @Test
    void shouldCreateAppointmentWhenVetIsFree() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        AppUser client = user(1L, UserRole.CLIENT);
        AppUser vet = user(2L, UserRole.VET);

        when(appUserService.getLoggedInUser()).thenReturn(client);
        when(appUserRepository.findById(2L)).thenReturn(Optional.of(vet));
        when(appointmentRepository.existsOverlappingAppointment(2L, startTime, endTime))
                .thenReturn(false);

        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Appointment result = appointmentService.createAppointment(2L, startTime);

        assertEquals(client, result.getUser());
        assertEquals(vet, result.getVet());
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
    }

    @Test
    void shouldThrowExceptionWhenVetIsOccupied() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1); //plus 1 dzien aby nie wywalilo przez sprawdzanie dat, bo minie milisekunda i juz bomba.
        LocalDateTime endTime = startTime.plusHours(1);

        AppUser client = user(1L, UserRole.CLIENT);
        AppUser vet = user(2L, UserRole.VET);

        when(appUserService.getLoggedInUser()).thenReturn(client);
        when(appUserRepository.findById(2L)).thenReturn(Optional.of(vet));
        when(appointmentRepository.existsOverlappingAppointment(2L, startTime, endTime))
                .thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> appointmentService.createAppointment(2L, startTime));
    }

    @Test
    void shouldThrowExceptionWhenAppointmentIsInPast() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> appointmentService.createAppointment(2L, startTime));
    }

    private AppUser user(Long id, UserRole role) {
        AppUser user = new AppUser();
        user.setId(id);
        user.setRole(role);
        user.setLogin("user" + id);
        user.setPassword("{noop}password");
        return user;
    }
}
