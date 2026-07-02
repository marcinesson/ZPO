package pl.agd.agd.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.agd.agd.model.ApplianceRepair;

public interface ApplianceRepairRepository extends JpaRepository<ApplianceRepair, Long> {
    List<ApplianceRepair> findByUserId(Long userId);

    @Query("""
            select count(record) > 0 from ApplianceRepair record
            where record.resource.id = :resourceId
            and record.startTime < :endTime
            and record.endTime > :startTime
            """)
    boolean existsOverlapping(@Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
