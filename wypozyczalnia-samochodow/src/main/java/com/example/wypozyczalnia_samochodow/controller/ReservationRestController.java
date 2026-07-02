package com.example.wypozyczalnia_samochodow.controller;

import com.example.wypozyczalnia_samochodow.model.Reservation;
import com.example.wypozyczalnia_samochodow.repository.CarRepository;
import com.example.wypozyczalnia_samochodow.repository.ReservationRepository;
import com.example.wypozyczalnia_samochodow.service.PricingAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Rezerwacje API", description = "Endpointy do zarządzania rezerwacjami samochodów")
public class ReservationRestController {

    private final ReservationRepository resRepo;
    private final CarRepository carRepo;
    private final PricingAlgorithm pricingAlgo;

    public ReservationRestController(ReservationRepository resRepo, CarRepository carRepo, PricingAlgorithm pricingAlgo) {
        this.resRepo = resRepo;
        this.carRepo = carRepo;
        this.pricingAlgo = pricingAlgo;
    }

    @PostMapping("/reservations")
    public ModelAndView createReservationUi(@ModelAttribute Reservation reservation) {
        reservation.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // Używamy orElseThrow() zamiast get() - standard czystego kodu w Springu!
        BigDecimal pricePerDay = carRepo.findById(reservation.getCar().getId()).orElseThrow().getPricePerDay();

        BigDecimal total = pricingAlgo.calculateTotalCost(
                pricePerDay,
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.isYoungDriver()
        );
        reservation.setTotalCost(total);
        resRepo.save(reservation);

        return new ModelAndView("redirect:/panel");
    }

    @Operation(summary = "Złóż nową rezerwację (Zwraca JSON)", description = "Czysty endpoint REST")
    @PostMapping("/reservations/swagger")
    public Reservation createReservationApi(@RequestBody Reservation reservation) {

        // BEZ IF: W pełni funkcyjna obsługa nazwy użytkownika ze Swaggera
        String username = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName())
                .filter(name -> !name.equals("anonymousUser"))
                .orElse("test_swagger_user");

        reservation.setUsername(username);

        BigDecimal pricePerDay = carRepo.findById(reservation.getCar().getId()).orElseThrow().getPricePerDay();
        BigDecimal total = pricingAlgo.calculateTotalCost(
                pricePerDay,
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.isYoungDriver()
        );
        reservation.setTotalCost(total);

        return resRepo.save(reservation);
    }
}