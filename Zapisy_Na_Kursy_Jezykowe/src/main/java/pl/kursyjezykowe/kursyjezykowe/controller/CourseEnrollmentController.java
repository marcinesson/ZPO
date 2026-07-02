package pl.kursyjezykowe.kursyjezykowe.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.kursyjezykowe.kursyjezykowe.model.LanguageType;
import pl.kursyjezykowe.kursyjezykowe.model.CourseEnrollment;
import pl.kursyjezykowe.kursyjezykowe.service.CourseEnrollmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseEnrollmentController {
    private final CourseEnrollmentService service;

    @PostMapping
    public String create(@RequestParam LanguageType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        CourseEnrollment record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<CourseEnrollment> all() {
        return service.getAllRecords();
    }
}
