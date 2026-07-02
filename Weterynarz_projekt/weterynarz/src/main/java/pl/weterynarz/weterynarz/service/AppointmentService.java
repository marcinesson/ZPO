package pl.weterynarz.weterynarz.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.weterynarz.weterynarz.dto.AppointmentScheduleResponse;
import pl.weterynarz.weterynarz.model.AppUser;
import pl.weterynarz.weterynarz.model.Appointment;
import pl.weterynarz.weterynarz.model.UserRole;
import pl.weterynarz.weterynarz.repository.AppUserRepository;
import pl.weterynarz.weterynarz.repository.AppointmentRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    private void validateTime(LocalDateTime startTime) {
        Optional.ofNullable(startTime)
        .filter(time -> !time.isBefore(LocalDateTime.now()))
        .orElseThrow(() -> new IllegalArgumentException("Wrong appointment dates."));
    }

    public Appointment createAppointment(Long vetId, LocalDateTime startTime) {
        validateTime(startTime);
        LocalDateTime endTime = startTime.plusHours(1);

        AppUser user = appUserService.getLoggedInUser();
        AppUser vet = appUserRepository.findById(vetId)
                .orElseThrow(() -> new RuntimeException("Vet not found."));

        Optional.of(appointmentRepository.existsOverlappingAppointment(vetId, startTime, endTime))
        .filter(isOccupied -> !isOccupied)
        .orElseThrow(() -> new IllegalStateException("Vet is occupied at that time."));

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setVet(vet);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        
        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found."));
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getLoggedInUserAppointments() {
        AppUser user = appUserService.getLoggedInUser();
        return appointmentRepository.findByUserOrderByStartTimeAsc(user);
    }

    public List<Appointment> getLoggedInVetAppointments() {
        AppUser vet = appUserService.getLoggedInUser();
        return appointmentRepository.findByVetIdOrderByStartTimeAsc(vet.getId());
    }

    public List<AppointmentScheduleResponse> getVetSchedule(Long vetId) {
        return appointmentRepository.findByVetIdOrderByStartTimeAsc(vetId)
        .stream()
        .map(appointment -> new AppointmentScheduleResponse(
            appointment.getStartTime(),
            appointment.getEndTime(),
            appointment.getVet().getLogin()
        )).toList();
    }

    public List<AppUser> getAllVets() {
        return appUserRepository.findByRole(UserRole.VET);
    }
}
