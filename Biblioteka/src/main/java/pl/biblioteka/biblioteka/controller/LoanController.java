package pl.biblioteka.biblioteka.controller;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.biblioteka.biblioteka.model.Loan;
import pl.biblioteka.biblioteka.service.LoanService;

@RestController @RequiredArgsConstructor
public class LoanController {
    private final LoanService service;

    @PostMapping("/api/books")
    public String create(@RequestParam Long itemId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Loan record = service.createRecord(itemId, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/api/books/all")
    public java.util.List<Loan> all() {
        return service.getAllRecords();
    }
}
