package pl.stoliki.stoliki.controller;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.stoliki.stoliki.model.TableReservation;
import pl.stoliki.stoliki.service.TableReservationService;

@RestController @RequiredArgsConstructor
public class TableReservationController {
    private final TableReservationService service;

    @PostMapping("/api/tables")
    public String create(@RequestParam Long itemId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        TableReservation record = service.createRecord(itemId, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/api/tables/all")
    public java.util.List<TableReservation> all() {
        return service.getAllRecords();
    }
}
