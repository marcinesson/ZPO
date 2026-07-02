package com.example.weterynarz.controller;

import com.example.weterynarz.model.Appointment;
import com.example.weterynarz.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VetRestController {

    private final AppointmentRepository apptRepo;

    public VetRestController(AppointmentRepository apptRepo) {
        this.apptRepo = apptRepo;
    }

    // Endpoint RESTowy dla Swaggera - pobiera grafik wybranego lekarza
    @GetMapping("/vets/{id}/schedule")
    public List<ScheduleDto> getVetSchedule(@PathVariable Long id) {
        return apptRepo.findByVetId(id).stream()
                .map(appt -> new ScheduleDto(appt.getStartTime(), appt.getEndTime()))
                .toList();
    }

    // Prosty obiekt (DTO) by nie wysyłać na zewnątrz danych usera
    record ScheduleDto(LocalDateTime start, LocalDateTime end) {}
}