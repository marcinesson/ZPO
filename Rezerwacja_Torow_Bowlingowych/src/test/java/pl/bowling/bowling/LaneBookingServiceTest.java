package pl.bowling.bowling;

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

import pl.bowling.bowling.model.*;
import pl.bowling.bowling.repository.LaneBookingRepository;
import pl.bowling.bowling.repository.BowlingLaneRepository;
import pl.bowling.bowling.service.AppUserService;
import pl.bowling.bowling.service.LaneBookingService;

@ExtendWith(MockitoExtension.class)
class LaneBookingServiceTest {
    @Mock
    private BowlingLaneRepository resourceRepository;

    @Mock
    private LaneBookingRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private LaneBookingService service;

    @Test
    void shouldChooseSmallestMatchingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, LaneType.STANDARD, 5, "100.00"),
                resource(2L, LaneType.STANDARD, 2, "80.00"),
                resource(3L, LaneType.CHILDREN, 1, "60.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        BowlingLane result = service.findBestResource(LaneType.STANDARD, 2, start, end);

        assertEquals(2L, result.getId());
        assertEquals(2, result.getCapacity());
    }

    @Test
    void shouldSkipOverlappingResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, LaneType.STANDARD, 2, "80.00"),
                resource(2L, LaneType.STANDARD, 4, "120.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        BowlingLane result = service.findBestResource(LaneType.STANDARD, 2, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldThrowWhenNothingMatches() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, LaneType.CHILDREN, 1, "50.00")));

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(LaneType.STANDARD, 3, start, end));
    }

    @Test
    void shouldCalculatePriceForStartedHoursAndCapacityMultiplier() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusMinutes(90);
        BowlingLane resource = resource(1L, LaneType.STANDARD, 5, "100.00");

        BigDecimal result = service.calculatePrice(resource, 2, start, end);

        assertEquals(new BigDecimal("110.00"), result);
    }

    private BowlingLane resource(Long id, LaneType category, int capacity, String price) {
        BowlingLane resource = new BowlingLane();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setPrice(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
