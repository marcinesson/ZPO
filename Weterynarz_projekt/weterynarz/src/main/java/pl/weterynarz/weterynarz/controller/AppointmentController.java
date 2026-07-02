package pl.weterynarz.weterynarz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.weterynarz.weterynarz.dto.AppointmentScheduleResponse;
import pl.weterynarz.weterynarz.service.AppointmentService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/appointments/vet-availability/{vetId}")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentScheduleResponse> showVetAppointments(@PathVariable Long vetId) {  
        return appointmentService.getVetSchedule(vetId);
    }
}
