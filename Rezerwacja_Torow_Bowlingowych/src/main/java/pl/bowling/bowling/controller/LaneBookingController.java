package pl.bowling.bowling.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.bowling.bowling.model.LaneType;
import pl.bowling.bowling.model.LaneBooking;
import pl.bowling.bowling.service.LaneBookingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lanes")
public class LaneBookingController {
    private final LaneBookingService service;

    @PostMapping
    public String create(@RequestParam LaneType category,
            @RequestParam int requiredCapacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        LaneBooking record = service.createRecord(category, requiredCapacity, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/all")
    public List<LaneBooking> all() {
        return service.getAllRecords();
    }
}
