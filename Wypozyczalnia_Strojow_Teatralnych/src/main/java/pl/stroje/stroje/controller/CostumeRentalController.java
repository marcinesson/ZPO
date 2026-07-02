package pl.stroje.stroje.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.stroje.stroje.model.CostumeType;
import pl.stroje.stroje.model.CostumeRental;
import pl.stroje.stroje.service.CostumeRentalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/costumes")
public class CostumeRentalController {
    private final CostumeRentalService service;

    @PostMapping
    public String create(@RequestParam CostumeType category,
            @RequestParam int requiredCapacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        CostumeRental record = service.createRecord(category, requiredCapacity, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/all")
    public List<CostumeRental> all() {
        return service.getAllRecords();
    }
}
