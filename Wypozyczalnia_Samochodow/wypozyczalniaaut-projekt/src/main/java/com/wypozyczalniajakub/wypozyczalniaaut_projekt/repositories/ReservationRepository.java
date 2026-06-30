package com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByCarIdAndEndDateGreaterThanEqual(Long carId, LocalDate date);

    @Query("""
       SELECT COUNT(r) > 0
       FROM Reservation r
       WHERE r.car.id = :carId
       AND r.startDate <= :endDate
       AND r.endDate >= :startDate
       """)
boolean existsOverlappingReservation( // jesli false to jest wolne
        @Param("carId") Long carId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);

    List<Reservation> findByUserId(Long userId);
}
