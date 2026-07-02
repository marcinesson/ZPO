package pl.przeprowadzki.przeprowadzki.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.przeprowadzki.przeprowadzki.model.MoveType;
import pl.przeprowadzki.przeprowadzki.model.MovingOrder;
import pl.przeprowadzki.przeprowadzki.service.MovingOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moving")
public class MovingOrderController {
    private final MovingOrderService service;

    @PostMapping
    public String create(@RequestParam MoveType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        MovingOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<MovingOrder> all() {
        return service.getAllRecords();
    }
}
