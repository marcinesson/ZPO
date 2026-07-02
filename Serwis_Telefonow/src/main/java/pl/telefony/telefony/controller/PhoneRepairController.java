package pl.telefony.telefony.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.telefony.telefony.model.PhoneIssueType;
import pl.telefony.telefony.model.PhoneRepair;
import pl.telefony.telefony.service.PhoneRepairService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/phone-repairs")
public class PhoneRepairController {
    private final PhoneRepairService service;

    @PostMapping
    public String create(@RequestParam PhoneIssueType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        PhoneRepair record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<PhoneRepair> all() {
        return service.getAllRecords();
    }
}
