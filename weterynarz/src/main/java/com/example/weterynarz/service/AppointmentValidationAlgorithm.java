package com.example.weterynarz.service;

import com.example.weterynarz.model.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentValidationAlgorithm {

    // Algorytm w 100% funkcyjny - ZERO instrukcji 'if'
    public boolean isTimeSlotAvailable(LocalDateTime newStart, LocalDateTime newEnd, List<Appointment> existingAppointments) {

        return Optional.ofNullable(newStart)
                // Sprawdzamy czy end nie jest nullem
                .filter(start -> newEnd != null)
                // Sprawdzamy czy start jest przed czasem końca
                .filter(start -> start.isBefore(newEnd))
                // Jeśli powyższe warunki są spełnione (brak if-a!), odpalamy strumień
                .map(start -> existingAppointments.stream()
                        .noneMatch(existing -> start.isBefore(existing.getEndTime()) && newEnd.isAfter(existing.getStartTime()))
                )
                // Jeśli filtry odrzuciły datę (był null lub zła kolejność), po prostu zwracamy false
                .orElse(false);
    }
}