package com.example.weterynarz.controller;

import org.springframework.validation.BindingResult;
import com.example.weterynarz.model.Appointment;
import com.example.weterynarz.model.Vet;
import com.example.weterynarz.repository.AppointmentRepository;
import com.example.weterynarz.repository.VetRepository;
import com.example.weterynarz.service.AppointmentValidationAlgorithm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UiController {

    private final VetRepository vetRepo;
    private final AppointmentRepository apptRepo;
    private final AppointmentValidationAlgorithm algo;

    public UiController(VetRepository vetRepo, AppointmentRepository apptRepo, AppointmentValidationAlgorithm algo) {
        this.vetRepo = vetRepo;
        this.apptRepo = apptRepo;
        this.algo = algo;
    }

    @GetMapping("/panel")
    public String viewPanel(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String success) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("vets", vetRepo.findAll());
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("error", error);
        model.addAttribute("success", success);

        // BRAK IF: Operator trójargumentowy zamiast bloku warunkowego
        model.addAttribute("appointments", isAdmin ? apptRepo.findAll() : apptRepo.findByUsername(username));

        return "panel";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment, BindingResult result, @RequestParam(required = false) Long vetId) {

        // BRAK IF: Cała drabinka warunków zastąpiona funkcyjnym łańcuchem Optional
        return Optional.ofNullable(vetId)
                // Przepuszczamy dalej tylko, gdy nie ma błędów formularza
                .filter(id -> !result.hasErrors())
                // Szukamy weterynarza w bazie
                .map(id -> vetRepo.findById(id).orElseThrow())
                // Przepuszczamy dalej tylko, gdy algorytm potwierdzi wolny termin
                .filter(vet -> algo.isTimeSlotAvailable(appointment.getStartTime(), appointment.getEndTime(), apptRepo.findByVetId(vet.getId())))
                // Jeśli wszystko poszło dobrze, przypisujemy dane i zapisujemy do bazy
                .map(vet -> {
                    appointment.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
                    appointment.setVet(vet);
                    apptRepo.save(appointment);
                    return "redirect:/panel?success=Wizyta_zostala_umowiona";
                })
                // Jeśli na którymkolwiek z etapów filtr odrzucił akcję (null, błąd, zajęty termin), zwracamy ogólny błąd
                .orElse("redirect:/panel?error=Wystapil_blad_formularza_lub_termin_zajety");
    }
}