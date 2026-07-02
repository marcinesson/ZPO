package pl.weterynarz.weterynarz.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.weterynarz.weterynarz.model.AppUser;
import pl.weterynarz.weterynarz.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("""
            SELECT COUNT(a) > 0
       FROM Appointment a
       WHERE a.vet.id = :vetId
       AND a.startTime < :endTime
       AND a.endTime > :startTime
            """)
    boolean existsOverlappingAppointment( // jesli false to jest wolne
        @Param("vetId") Long vetId,
        @Param("startTime") LocalDateTime startDate,
        @Param("endTime") LocalDateTime endDate);

    List<Appointment> findByVetIdOrderByStartTimeAsc(Long vetId);

    List<Appointment> findByUserOrderByStartTimeAsc(AppUser user);
}
