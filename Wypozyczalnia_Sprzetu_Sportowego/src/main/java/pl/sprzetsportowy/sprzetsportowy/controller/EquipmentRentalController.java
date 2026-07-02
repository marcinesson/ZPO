package pl.sprzetsportowy.sprzetsportowy.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.sprzetsportowy.sprzetsportowy.model.SportType;
import pl.sprzetsportowy.sprzetsportowy.model.EquipmentRental;
import pl.sprzetsportowy.sprzetsportowy.service.EquipmentRentalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipment")
public class EquipmentRentalController {
    private final EquipmentRentalService service;

    @PostMapping
    public String create(@RequestParam SportType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        EquipmentRental record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<EquipmentRental> all() {
        return service.getAllRecords();
    }
}
