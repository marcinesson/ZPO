package pl.narzedzia.narzedzia.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.narzedzia.narzedzia.model.ToolType;
import pl.narzedzia.narzedzia.model.ToolRental;
import pl.narzedzia.narzedzia.service.ToolRentalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tools")
public class ToolRentalController {
    private final ToolRentalService service;

    @PostMapping
    public String create(@RequestParam ToolType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        ToolRental record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<ToolRental> all() {
        return service.getAllRecords();
    }
}
