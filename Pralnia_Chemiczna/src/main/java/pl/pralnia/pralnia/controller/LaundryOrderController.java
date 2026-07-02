package pl.pralnia.pralnia.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.pralnia.pralnia.model.LaundryType;
import pl.pralnia.pralnia.model.LaundryOrder;
import pl.pralnia.pralnia.service.LaundryOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laundry")
public class LaundryOrderController {
    private final LaundryOrderService service;

    @PostMapping
    public String create(@RequestParam LaundryType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        LaundryOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<LaundryOrder> all() {
        return service.getAllRecords();
    }
}
