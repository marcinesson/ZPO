package pl.stroje.stroje;

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

import pl.stroje.stroje.model.*;
import pl.stroje.stroje.repository.CostumeRentalRepository;
import pl.stroje.stroje.repository.CostumeRepository;
import pl.stroje.stroje.service.AppUserService;
import pl.stroje.stroje.service.CostumeRentalService;

@ExtendWith(MockitoExtension.class)
class CostumeRentalServiceTest {
    @Mock
    private CostumeRepository resourceRepository;

    @Mock
    private CostumeRentalRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private CostumeRentalService service;

    @Test
    void shouldChooseSmallestMatchingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, CostumeType.HISTORICAL, 5, "100.00"),
                resource(2L, CostumeType.HISTORICAL, 2, "80.00"),
                resource(3L, CostumeType.FANTASY, 1, "60.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        Costume result = service.findBestResource(CostumeType.HISTORICAL, 2, start, end);

        assertEquals(2L, result.getId());
        assertEquals(2, result.getCapacity());
    }

    @Test
    void shouldSkipOverlappingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, CostumeType.HISTORICAL, 2, "80.00"),
                resource(2L, CostumeType.HISTORICAL, 4, "120.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        Costume result = service.findBestResource(CostumeType.HISTORICAL, 2, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldThrowWhenNothingMatches() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, CostumeType.FANTASY, 1, "50.00")));

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(CostumeType.HISTORICAL, 3, start, end));
    }

    @Test
    void shouldCalculatePriceForStartedHoursAndCapacityMultiplier() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusMinutes(90);
        Costume resource = resource(1L, CostumeType.HISTORICAL, 5, "100.00");

        BigDecimal result = service.calculatePrice(resource, 2, start, end);

        assertEquals(new BigDecimal("110.00"), result);
    }

    private Costume resource(Long id, CostumeType category, int capacity, String price) {
        Costume resource = new Costume();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setPrice(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
