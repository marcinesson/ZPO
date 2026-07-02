package pl.hotel.hotel.controller;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.hotel.hotel.model.RoomBooking;
import pl.hotel.hotel.service.RoomBookingService;

@RestController @RequiredArgsConstructor
public class RoomBookingController {
    private final RoomBookingService service;

    @PostMapping("/api/rooms")
    public String create(@RequestParam Long itemId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        RoomBooking record = service.createRecord(itemId, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/api/rooms/all")
    public java.util.List<RoomBooking> all() {
        return service.getAllRecords();
    }
}
