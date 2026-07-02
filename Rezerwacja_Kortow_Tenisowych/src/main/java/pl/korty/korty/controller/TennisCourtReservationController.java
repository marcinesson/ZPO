package pl.korty.korty.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.korty.korty.model.CourtSurface;
import pl.korty.korty.model.TennisCourtReservation;
import pl.korty.korty.service.TennisCourtReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courts")
public class TennisCourtReservationController {
    private final TennisCourtReservationService service;

    @PostMapping
    public String create(@RequestParam CourtSurface category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        TennisCourtReservation record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<TennisCourtReservation> all() {
        return service.getAllRecords();
    }
}
