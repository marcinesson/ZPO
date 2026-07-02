package pl.fitness.fitness.controller;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.fitness.fitness.model.ClassEnrollment;
import pl.fitness.fitness.service.ClassEnrollmentService;

@RestController @RequiredArgsConstructor
public class ClassEnrollmentController {
    private final ClassEnrollmentService service;

    @PostMapping("/api/classes")
    public String create(@RequestParam Long itemId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        ClassEnrollment record = service.createRecord(itemId, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/api/classes/all")
    public java.util.List<ClassEnrollment> all() {
        return service.getAllRecords();
    }
}
