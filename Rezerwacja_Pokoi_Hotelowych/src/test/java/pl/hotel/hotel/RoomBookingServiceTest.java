package pl.hotel.hotel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hotel.hotel.model.*;
import pl.hotel.hotel.repository.*;
import pl.hotel.hotel.service.*;

@ExtendWith(MockitoExtension.class)
class RoomBookingServiceTest {
    @Mock HotelRoomRepository itemRepository;
    @Mock RoomBookingRepository recordRepository;
    @Mock AppUserService appUserService;
    @InjectMocks RoomBookingService service;

    @Test
    void shouldFindBestAvailableItem() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);
        HotelRoom small = new HotelRoom(1L, "Small", 2, new BigDecimal("10.00"), true);
        HotelRoom big = new HotelRoom(2L, "Big", 5, new BigDecimal("20.00"), true);
        when(itemRepository.findAll()).thenReturn(List.of(big, small));
        when(recordRepository.findAll()).thenReturn(List.of());
        assertEquals(small, service.findBestAvailableItem(2, start, end));
    }
}
