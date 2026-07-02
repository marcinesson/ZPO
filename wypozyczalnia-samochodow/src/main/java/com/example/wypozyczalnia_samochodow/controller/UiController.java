package com.example.wypozyczalnia_samochodow.controller;

import com.example.wypozyczalnia_samochodow.model.Reservation;
import com.example.wypozyczalnia_samochodow.repository.CarRepository;
import com.example.wypozyczalnia_samochodow.repository.ReservationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    private final CarRepository carRepo;
    private final ReservationRepository resRepo;

    public UiController(CarRepository carRepo, ReservationRepository resRepo) {
        this.carRepo = carRepo;
        this.resRepo = resRepo;
    }

    @GetMapping("/panel")
    public String viewPanel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("cars", carRepo.findAll());
        model.addAttribute("newReservation", new Reservation());

        // BEZ IF: Operator trójargumentowy (Jeśli admin -> wszystko, w przeciwnym razie -> tylko jego)
        model.addAttribute("reservations", isAdmin ? resRepo.findAll() : resRepo.findByUsername(username));

        return "panel";
    }
}