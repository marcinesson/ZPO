package pl.fitness.fitness;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fitness.fitness.model.*;
import pl.fitness.fitness.repository.*;
import pl.fitness.fitness.service.*;

@ExtendWith(MockitoExtension.class)
class ClassEnrollmentServiceTest {
    @Mock FitnessClassRepository itemRepository;
    @Mock ClassEnrollmentRepository recordRepository;
    @Mock AppUserService appUserService;
    @InjectMocks ClassEnrollmentService service;

    @Test
    void shouldFindBestAvailableItem() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);
        FitnessClass small = new FitnessClass(1L, "Small", 2, new BigDecimal("10.00"), true);
        FitnessClass big = new FitnessClass(2L, "Big", 5, new BigDecimal("20.00"), true);
        when(itemRepository.findAll()).thenReturn(List.of(big, small));
        when(recordRepository.findAll()).thenReturn(List.of());
        assertEquals(small, service.findBestAvailableItem(2, start, end));
    }
}
