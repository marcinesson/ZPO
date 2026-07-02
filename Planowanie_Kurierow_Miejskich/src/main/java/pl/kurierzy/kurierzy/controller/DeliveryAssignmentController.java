package pl.kurierzy.kurierzy.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.kurierzy.kurierzy.model.DeliveryZone;
import pl.kurierzy.kurierzy.model.DeliveryAssignment;
import pl.kurierzy.kurierzy.service.DeliveryAssignmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryAssignmentController {
    private final DeliveryAssignmentService service;

    @PostMapping
    public String create(@RequestParam DeliveryZone category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        DeliveryAssignment record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<DeliveryAssignment> all() {
        return service.getAllRecords();
    }
}
