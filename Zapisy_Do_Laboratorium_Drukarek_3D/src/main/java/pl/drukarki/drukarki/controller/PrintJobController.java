package pl.drukarki.drukarki.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.drukarki.drukarki.model.MaterialType;
import pl.drukarki.drukarki.model.PrintJob;
import pl.drukarki.drukarki.service.PrintJobService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prints")
public class PrintJobController {
    private final PrintJobService service;

    @PostMapping
    public String create(@RequestParam MaterialType category,
            @RequestParam int requiredCapacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        PrintJob record = service.createRecord(category, requiredCapacity, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/all")
    public List<PrintJob> all() {
        return service.getAllRecords();
    }
}
