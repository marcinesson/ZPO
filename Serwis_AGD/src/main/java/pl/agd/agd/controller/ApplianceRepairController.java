package pl.agd.agd.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.agd.agd.model.ApplianceType;
import pl.agd.agd.model.ApplianceRepair;
import pl.agd.agd.service.ApplianceRepairService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repairs")
public class ApplianceRepairController {
    private final ApplianceRepairService service;

    @PostMapping
    public String create(@RequestParam ApplianceType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        ApplianceRepair record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<ApplianceRepair> all() {
        return service.getAllRecords();
    }
}
