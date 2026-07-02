package pl.foto.foto.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.foto.foto.model.SessionType;
import pl.foto.foto.model.PhotoSession;
import pl.foto.foto.service.PhotoSessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoSessionController {
    private final PhotoSessionService service;

    @PostMapping
    public String create(@RequestParam SessionType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        PhotoSession record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<PhotoSession> all() {
        return service.getAllRecords();
    }
}
