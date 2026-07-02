package pl.serwisrowerowy.serwisrowerowy.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.serwisrowerowy.serwisrowerowy.model.RepairType;
import pl.serwisrowerowy.serwisrowerowy.model.BikeRepairOrder;
import pl.serwisrowerowy.serwisrowerowy.service.BikeRepairOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repairs")
public class BikeRepairOrderController {
    private final BikeRepairOrderService service;

    @PostMapping
    public String create(@RequestParam RepairType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        BikeRepairOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<BikeRepairOrder> all() {
        return service.getAllRecords();
    }
}
