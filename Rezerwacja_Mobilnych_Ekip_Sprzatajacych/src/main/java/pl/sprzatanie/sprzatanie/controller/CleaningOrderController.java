package pl.sprzatanie.sprzatanie.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.sprzatanie.sprzatanie.model.CleaningType;
import pl.sprzatanie.sprzatanie.model.CleaningOrder;
import pl.sprzatanie.sprzatanie.service.CleaningOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cleaning")
public class CleaningOrderController {
    private final CleaningOrderService service;

    @PostMapping
    public String create(@RequestParam CleaningType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        CleaningOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<CleaningOrder> all() {
        return service.getAllRecords();
    }
}
