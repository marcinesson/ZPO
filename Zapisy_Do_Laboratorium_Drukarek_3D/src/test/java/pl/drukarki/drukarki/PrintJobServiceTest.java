package pl.drukarki.drukarki;

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

import pl.drukarki.drukarki.model.*;
import pl.drukarki.drukarki.repository.PrintJobRepository;
import pl.drukarki.drukarki.repository.PrinterStationRepository;
import pl.drukarki.drukarki.service.AppUserService;
import pl.drukarki.drukarki.service.PrintJobService;

@ExtendWith(MockitoExtension.class)
class PrintJobServiceTest {
    @Mock
    private PrinterStationRepository resourceRepository;

    @Mock
    private PrintJobRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private PrintJobService service;

    @Test
    void shouldChooseSmallestMatchingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, MaterialType.PLA, 5, "100.00"),
                resource(2L, MaterialType.PLA, 2, "80.00"),
                resource(3L, MaterialType.PETG, 1, "60.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        PrinterStation result = service.findBestResource(MaterialType.PLA, 2, start, end);

        assertEquals(2L, result.getId());
        assertEquals(2, result.getCapacity());
    }

    @Test
    void shouldSkipOverlappingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, MaterialType.PLA, 2, "80.00"),
                resource(2L, MaterialType.PLA, 4, "120.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        PrinterStation result = service.findBestResource(MaterialType.PLA, 2, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldThrowWhenNothingMatches() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, MaterialType.PETG, 1, "50.00")));

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(MaterialType.PLA, 3, start, end));
    }

    @Test
    void shouldCalculatePriceForStartedHoursAndCapacityMultiplier() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusMinutes(90);
        PrinterStation resource = resource(1L, MaterialType.PLA, 5, "100.00");

        BigDecimal result = service.calculatePrice(resource, 2, start, end);

        assertEquals(new BigDecimal("110.00"), result);
    }

    private PrinterStation resource(Long id, MaterialType category, int capacity, String price) {
        PrinterStation resource = new PrinterStation();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setPrice(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
