package com.example.wypozyczalnia_samochodow.repository;

import com.example.wypozyczalnia_samochodow.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUsername(String username);
}