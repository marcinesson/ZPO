package pl.plywanie.plywanie;

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

import pl.plywanie.plywanie.model.*;
import pl.plywanie.plywanie.repository.SwimmingEnrollmentRepository;
import pl.plywanie.plywanie.repository.SwimmingGroupRepository;
import pl.plywanie.plywanie.service.AppUserService;
import pl.plywanie.plywanie.service.SwimmingEnrollmentService;

@ExtendWith(MockitoExtension.class)
class SwimmingEnrollmentServiceTest {
    @Mock
    private SwimmingGroupRepository resourceRepository;

    @Mock
    private SwimmingEnrollmentRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private SwimmingEnrollmentService service;

    @Test
    void shouldChooseBestScoredResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, SwimmingLevel.BEGINNER, 10, 5, 5, "100.00"),
                resource(2L, SwimmingLevel.BEGINNER, 3, 3, 5, "70.00"),
                resource(3L, SwimmingLevel.INTERMEDIATE, 3, 3, 5, "50.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        SwimmingGroup result = service.findBestResource(SwimmingLevel.BEGINNER,
                3, 3, 10, 1, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldSkipOverlappingBetterResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, SwimmingLevel.BEGINNER, 3, 3, 5, "70.00"),
                resource(2L, SwimmingLevel.BEGINNER, 5, 4, 5, "90.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        SwimmingGroup result = service.findBestResource(SwimmingLevel.BEGINNER,
                3, 3, 10, 1, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldRejectWhenTimeWindowIsTooShort() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, SwimmingLevel.BEGINNER, 3, 3, 5, "70.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(SwimmingLevel.BEGINNER,
                        3, 3, 20, 1, start, end));
    }

    @Test
    void shouldCalculateStartedRequiredHoursAndPrice() {
        SwimmingGroup resource = resource(1L, SwimmingLevel.BEGINNER,
                3, 3, 5, "100.00");

        assertEquals(2, service.calculateRequiredHours(resource, 6));
        assertEquals(new BigDecimal("220.00"), service.calculatePrice(resource, 6, 3));
    }

    private SwimmingGroup resource(Long id, SwimmingLevel category, int capacity,
            int quality, int unitsPerHour, String price) {
        SwimmingGroup resource = new SwimmingGroup();
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
