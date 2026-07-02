package pl.instrumenty.instrumenty.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.instrumenty.instrumenty.model.InstrumentType;
import pl.instrumenty.instrumenty.model.InstrumentRental;
import pl.instrumenty.instrumenty.service.InstrumentRentalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instruments")
public class InstrumentRentalController {
    private final InstrumentRentalService service;

    @PostMapping
    public String create(@RequestParam InstrumentType category,
            @RequestParam int requiredCapacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        InstrumentRental record = service.createRecord(category, requiredCapacity, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/all")
    public List<InstrumentRental> all() {
        return service.getAllRecords();
    }
}
