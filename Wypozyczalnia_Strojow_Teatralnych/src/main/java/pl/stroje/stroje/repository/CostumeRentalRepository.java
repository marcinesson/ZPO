package pl.stroje.stroje.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.stroje.stroje.model.CostumeRental;

public interface CostumeRentalRepository extends JpaRepository<CostumeRental, Long> {
    List<CostumeRental> findByUserId(Long userId);

    @Query("""
            select count(record) > 0 from CostumeRental record
            where record.resource.id = :resourceId
            and record.startTime < :endTime
            and record.endTime > :startTime
            """)
    boolean existsOverlapping(@Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
