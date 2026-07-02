package pl.szkielet.szkielet.controller;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.szkielet.szkielet.model.MainRecord;
import pl.szkielet.szkielet.service.MainRecordService;

@RestController @RequiredArgsConstructor
public class MainRecordController {
    private final MainRecordService service;

    @PostMapping("/api/items")
    public String create(@RequestParam Long itemId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        MainRecord record = service.createRecord(itemId, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/api/items/all")
    public java.util.List<MainRecord> all() {
        return service.getAllRecords();
    }
}
