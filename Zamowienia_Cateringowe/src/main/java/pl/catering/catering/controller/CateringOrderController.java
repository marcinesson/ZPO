package pl.catering.catering.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.catering.catering.model.DietType;
import pl.catering.catering.model.CateringOrder;
import pl.catering.catering.service.CateringOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catering")
public class CateringOrderController {
    private final CateringOrderService service;

    @PostMapping
    public String create(@RequestParam DietType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        CateringOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<CateringOrder> all() {
        return service.getAllRecords();
    }
}
