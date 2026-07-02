package pl.symulatory.symulatory;

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

import pl.symulatory.symulatory.model.*;
import pl.symulatory.symulatory.repository.VrSessionRepository;
import pl.symulatory.symulatory.repository.VrSimulatorRepository;
import pl.symulatory.symulatory.service.AppUserService;
import pl.symulatory.symulatory.service.VrSessionService;

@ExtendWith(MockitoExtension.class)
class VrSessionServiceTest {
    @Mock
    private VrSimulatorRepository resourceRepository;

    @Mock
    private VrSessionRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private VrSessionService service;

    @Test
    void shouldChooseBestScoredResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, VrScenario.RACING, 10, 5, 5, "100.00"),
                resource(2L, VrScenario.RACING, 3, 3, 5, "70.00"),
                resource(3L, VrScenario.FLIGHT, 3, 3, 5, "50.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        VrSimulator result = service.findBestResource(VrScenario.RACING,
                3, 3, 10, 1, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldSkipOverlappingBetterResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, VrScenario.RACING, 3, 3, 5, "70.00"),
                resource(2L, VrScenario.RACING, 5, 4, 5, "90.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        VrSimulator result = service.findBestResource(VrScenario.RACING,
                3, 3, 10, 1, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldRejectWhenTimeWindowIsTooShort() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, VrScenario.RACING, 3, 3, 5, "70.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(VrScenario.RACING,
                        3, 3, 20, 1, start, end));
    }

    @Test
    void shouldCalculateStartedRequiredHoursAndPrice() {
        VrSimulator resource = resource(1L, VrScenario.RACING,
                3, 3, 5, "100.00");

        assertEquals(2, service.calculateRequiredHours(resource, 6));
        assertEquals(new BigDecimal("220.00"), service.calculatePrice(resource, 6, 3));
    }

    private VrSimulator resource(Long id, VrScenario category, int capacity,
            int quality, int unitsPerHour, String price) {
        VrSimulator resource = new VrSimulator();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setQuality(quality);
        resource.setUnitsPerHour(unitsPerHour);
        resource.setPricePerHour(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
