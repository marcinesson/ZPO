package pl.drony.drony.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.drony.drony.model.InspectionDrone;

public interface InspectionDroneRepository extends JpaRepository<InspectionDrone, Long> {
}
