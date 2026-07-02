package pl.robotyka.robotyka.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.robotyka.robotyka.model.RobotType;
import pl.robotyka.robotyka.model.RoboticsWorkshop;
import pl.robotyka.robotyka.service.RoboticsWorkshopService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/robotics")
public class RoboticsWorkshopController {
    private final RoboticsWorkshopService service;

    @PostMapping
    public String create(@RequestParam RobotType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        RoboticsWorkshop record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<RoboticsWorkshop> all() {
        return service.getAllRecords();
    }
}
