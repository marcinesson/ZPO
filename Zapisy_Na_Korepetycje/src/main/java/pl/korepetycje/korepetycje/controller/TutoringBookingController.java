package pl.korepetycje.korepetycje.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.korepetycje.korepetycje.model.SubjectType;
import pl.korepetycje.korepetycje.model.TutoringBooking;
import pl.korepetycje.korepetycje.service.TutoringBookingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tutoring")
public class TutoringBookingController {
    private final TutoringBookingService service;

    @PostMapping
    public String create(@RequestParam SubjectType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        TutoringBooking record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<TutoringBooking> all() {
        return service.getAllRecords();
    }
}
