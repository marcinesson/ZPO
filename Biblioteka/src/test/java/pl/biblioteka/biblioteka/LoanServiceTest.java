package pl.biblioteka.biblioteka;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.biblioteka.biblioteka.model.*;
import pl.biblioteka.biblioteka.repository.*;
import pl.biblioteka.biblioteka.service.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    @Mock BookRepository itemRepository;
    @Mock LoanRepository recordRepository;
    @Mock AppUserService appUserService;
    @InjectMocks LoanService service;

    @Test
    void shouldFindBestAvailableItem() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);
        Book small = new Book(1L, "Small", 2, new BigDecimal("10.00"), true);
        Book big = new Book(2L, "Big", 5, new BigDecimal("20.00"), true);
        when(itemRepository.findAll()).thenReturn(List.of(big, small));
        when(recordRepository.findAll()).thenReturn(List.of());
        assertEquals(small, service.findBestAvailableItem(2, start, end));
    }
}
