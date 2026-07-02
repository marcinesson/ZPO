package pl.plywanie.plywanie.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.plywanie.plywanie.model.SwimmingLevel;
import pl.plywanie.plywanie.model.SwimmingEnrollment;
import pl.plywanie.plywanie.service.SwimmingEnrollmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/swimming")
public class SwimmingEnrollmentController {
    private final SwimmingEnrollmentService service;

    @PostMapping
    public String create(@RequestParam SwimmingLevel category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        SwimmingEnrollment record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<SwimmingEnrollment> all() {
        return service.getAllRecords();
    }
}
