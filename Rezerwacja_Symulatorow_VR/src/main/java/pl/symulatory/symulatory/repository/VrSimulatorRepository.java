package pl.symulatory.symulatory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.symulatory.symulatory.model.VrSimulator;

public interface VrSimulatorRepository extends JpaRepository<VrSimulator, Long> {
}
