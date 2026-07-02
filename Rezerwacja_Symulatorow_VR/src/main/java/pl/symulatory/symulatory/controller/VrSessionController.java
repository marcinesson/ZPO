package pl.symulatory.symulatory.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.symulatory.symulatory.model.VrScenario;
import pl.symulatory.symulatory.model.VrSession;
import pl.symulatory.symulatory.service.VrSessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/simulators")
public class VrSessionController {
    private final VrSessionService service;

    @PostMapping
    public String create(@RequestParam VrScenario category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        VrSession record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<VrSession> all() {
        return service.getAllRecords();
    }
}
