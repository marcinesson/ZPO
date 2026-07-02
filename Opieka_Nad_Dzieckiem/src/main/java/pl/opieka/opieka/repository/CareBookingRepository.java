package pl.opieka.opieka.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.opieka.opieka.model.CareBooking;

public interface CareBookingRepository extends JpaRepository<CareBooking, Long> {
    List<CareBooking> findByUserId(Long userId);

    @Query("""
            select count(record) > 0 from CareBooking record
            where record.resource.id = :resourceId
            and record.startTime < :endTime
            and record.endTime > :startTime
            """)
    boolean existsOverlapping(@Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
