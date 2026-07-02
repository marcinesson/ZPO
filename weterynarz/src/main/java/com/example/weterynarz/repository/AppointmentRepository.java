package com.example.weterynarz.repository;

import com.example.weterynarz.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUsername(String username); // Zwraca tylko wizyty usera
    List<Appointment> findByVetId(Long vetId); // Zwraca grafik konkretnego lekarza
}