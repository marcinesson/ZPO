package pl.agd.agd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agd.agd.model.ServiceTechnician;

public interface ServiceTechnicianRepository extends JpaRepository<ServiceTechnician, Long> {
}
