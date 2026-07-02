package pl.studio.studio;

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

import pl.studio.studio.model.*;
import pl.studio.studio.repository.StudioSessionRepository;
import pl.studio.studio.repository.StudioRoomRepository;
import pl.studio.studio.service.AppUserService;
import pl.studio.studio.service.StudioSessionService;

@ExtendWith(MockitoExtension.class)
class StudioSessionServiceTest {
    @Mock
    private StudioRoomRepository resourceRepository;

    @Mock
    private StudioSessionRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private StudioSessionService service;

    @Test
    void shouldChooseSmallestMatchingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, StudioType.PODCAST, 5, "100.00"),
                resource(2L, StudioType.PODCAST, 2, "80.00"),
                resource(3L, StudioType.VOCAL, 1, "60.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        StudioRoom result = service.findBestResource(StudioType.PODCAST, 2, start, end);

        assertEquals(2L, result.getId());
        assertEquals(2, result.getCapacity());
    }

    @Test
    void shouldSkipOverlappingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, StudioType.PODCAST, 2, "80.00"),
                resource(2L, StudioType.PODCAST, 4, "120.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        StudioRoom result = service.findBestResource(StudioType.PODCAST, 2, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldThrowWhenNothingMatches() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, StudioType.VOCAL, 1, "50.00")));

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(StudioType.PODCAST, 3, start, end));
    }

    @Test
    void shouldCalculatePriceForStartedHoursAndCapacityMultiplier() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusMinutes(90);
        StudioRoom resource = resource(1L, StudioType.PODCAST, 5, "100.00");

        BigDecimal result = service.calculatePrice(resource, 2, start, end);

        assertEquals(new BigDecimal("110.00"), result);
    }

    private StudioRoom resource(Long id, StudioType category, int capacity, String price) {
        StudioRoom resource = new StudioRoom();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setPrice(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
