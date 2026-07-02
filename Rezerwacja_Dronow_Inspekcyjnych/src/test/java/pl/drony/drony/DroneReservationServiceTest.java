package pl.drony.drony;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.drony.drony.model.*;
import pl.drony.drony.repository.DroneReservationRepository;
import pl.drony.drony.repository.InspectionDroneRepository;
import pl.drony.drony.service.AppUserService;
import pl.drony.drony.service.DroneReservationService;

@ExtendWith(MockitoExtension.class)
class DroneReservationServiceTest {
    @Mock
    private InspectionDroneRepository resourceRepository;

    @Mock
    private DroneReservationRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private DroneReservationService service;

    @Test
    void shouldChooseSmallestMatchingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, DroneType.PHOTO, 5, "100.00"),
                resource(2L, DroneType.PHOTO, 2, "80.00"),
                resource(3L, DroneType.THERMAL, 1, "60.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        InspectionDrone result = service.findBestResource(DroneType.PHOTO, 2, start, end);

        assertEquals(2L, result.getId());
        assertEquals(2, result.getCapacity());
    }

    @Test
    void shouldSkipOverlappingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, DroneType.PHOTO, 2, "80.00"),
                resource(2L, DroneType.PHOTO, 4, "120.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        InspectionDrone result = service.findBestResource(DroneType.PHOTO, 2, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldThrowWhenNothingMatches() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, DroneType.THERMAL, 1, "50.00")));

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(DroneType.PHOTO, 3, start, end));
    }

    @Test
    void shouldCalculatePriceForStartedHoursAndCapacityMultiplier() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusMinutes(90);
        InspectionDrone resource = resource(1L, DroneType.PHOTO, 5, "100.00");

        BigDecimal result = service.calculatePrice(resource, 2, start, end);

        assertEquals(new BigDecimal("110.00"), result);
    }

    private InspectionDrone resource(Long id, DroneType category, int capacity, String price) {
        InspectionDrone resource = new InspectionDrone();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setPrice(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
