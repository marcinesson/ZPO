package com.wypozyczalniajakub.wypozyczalniaaut_projekt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.service.ReservationService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public String createReservation(
        @RequestParam("carId") Long carId, 
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        Reservation reservation =  reservationService.createReservation(carId, startDate, endDate);

        return "Reservation created with an id: " + reservation.getId() + ", cost: " + reservation.getFinalCost();
    }
    
}
