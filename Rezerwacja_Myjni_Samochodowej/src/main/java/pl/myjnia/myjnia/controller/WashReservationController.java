package pl.myjnia.myjnia.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.myjnia.myjnia.model.WashType;
import pl.myjnia.myjnia.model.WashReservation;
import pl.myjnia.myjnia.service.WashReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/washes")
public class WashReservationController {
    private final WashReservationService service;

    @PostMapping
    public String create(@RequestParam WashType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        WashReservation record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<WashReservation> all() {
        return service.getAllRecords();
    }
}
