package pl.badania.badania.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.badania.badania.model.TestType;
import pl.badania.badania.model.LabTestBooking;
import pl.badania.badania.service.LabTestBookingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
public class LabTestBookingController {
    private final LabTestBookingService service;

    @PostMapping
    public String create(@RequestParam TestType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        LabTestBooking record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<LabTestBooking> all() {
        return service.getAllRecords();
    }
}
