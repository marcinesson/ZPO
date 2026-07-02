package pl.serwiskomputerowy.serwis.controller;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.serwiskomputerowy.serwis.model.RepairTicket;
import pl.serwiskomputerowy.serwis.service.RepairTicketService;

@RestController @RequiredArgsConstructor
public class RepairTicketController {
    private final RepairTicketService service;

    @PostMapping("/api/tickets")
    public String create(@RequestParam Long itemId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        RepairTicket record = service.createRecord(itemId, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice();
    }

    @GetMapping("/api/tickets/all")
    public java.util.List<RepairTicket> all() {
        return service.getAllRecords();
    }
}
