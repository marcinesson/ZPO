package pl.telefony.telefony.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.telefony.telefony.model.PhoneTechnician;

public interface PhoneTechnicianRepository extends JpaRepository<PhoneTechnician, Long> {
}
