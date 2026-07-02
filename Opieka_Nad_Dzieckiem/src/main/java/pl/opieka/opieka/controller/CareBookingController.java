package pl.opieka.opieka.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.opieka.opieka.model.CareType;
import pl.opieka.opieka.model.CareBooking;
import pl.opieka.opieka.service.CareBookingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/care")
public class CareBookingController {
    private final CareBookingService service;

    @PostMapping
    public String create(@RequestParam CareType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        CareBooking record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<CareBooking> all() {
        return service.getAllRecords();
    }
}
