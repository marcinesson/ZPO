package pl.studio.studio.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.studio.studio.model.StudioType;
import pl.studio.studio.model.StudioSession;
import pl.studio.studio.service.StudioSessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studios")
public class StudioSessionController {
    private final StudioSessionService service;

    @PostMapping
    public String create(@RequestParam StudioType category,
            @RequestParam int requiredCapacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        StudioSession record = service.createRecord(category, requiredCapacity, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/all")
    public List<StudioSession> all() {
        return service.getAllRecords();
    }
}
