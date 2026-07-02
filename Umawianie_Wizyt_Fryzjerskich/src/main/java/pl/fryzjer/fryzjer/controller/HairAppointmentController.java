package pl.fryzjer.fryzjer.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.fryzjer.fryzjer.model.HairServiceType;
import pl.fryzjer.fryzjer.model.HairAppointment;
import pl.fryzjer.fryzjer.service.HairAppointmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class HairAppointmentController {
    private final HairAppointmentService service;

    @PostMapping
    public String create(@RequestParam HairServiceType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        HairAppointment record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<HairAppointment> all() {
        return service.getAllRecords();
    }
}
