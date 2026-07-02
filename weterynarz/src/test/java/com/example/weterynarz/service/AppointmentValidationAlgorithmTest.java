package com.example.weterynarz.service;

import com.example.weterynarz.model.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentValidationAlgorithmTest {

    AppointmentValidationAlgorithm algo = new AppointmentValidationAlgorithm();

    @Test
    void shouldAllowBookingIfNoOverlap() {
        Appointment existing = new Appointment();
        existing.setStartTime(LocalDateTime.of(2026, 7, 10, 10, 0));
        existing.setEndTime(LocalDateTime.of(2026, 7, 10, 11, 0));

        // Próba rezerwacji OD 11:30 DO 12:00
        boolean result = algo.isTimeSlotAvailable(
                LocalDateTime.of(2026, 7, 10, 11, 30),
                LocalDateTime.of(2026, 7, 10, 12, 0),
                List.of(existing)
        );

        assertTrue(result);
    }

    @Test
    void shouldRejectBookingIfOverlapping() {
        Appointment existing = new Appointment();
        // Istniejąca wizyta 10:00 - 11:00
        existing.setStartTime(LocalDateTime.of(2026, 7, 10, 10, 0));
        existing.setEndTime(LocalDateTime.of(2026, 7, 10, 11, 0));

        // Nowa wizyta nachodzi (10:30 - 11:30)
        boolean result = algo.isTimeSlotAvailable(
                LocalDateTime.of(2026, 7, 10, 10, 30),
                LocalDateTime.of(2026, 7, 10, 11, 30),
                List.of(existing)
        );

        assertFalse(result); // Algorytm powinien zabronić
    }
}