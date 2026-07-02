package pl.drony.drony.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.drony.drony.model.DroneType;
import pl.drony.drony.model.DroneReservation;
import pl.drony.drony.service.DroneReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drones")
public class DroneReservationController {
    private final DroneReservationService service;

    @PostMapping
    public String create(@RequestParam DroneType category,
            @RequestParam int requiredCapacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        DroneReservation record = service.createRecord(category, requiredCapacity, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/all")
    public List<DroneReservation> all() {
        return service.getAllRecords();
    }
}
