package pl.badania.badania.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.badania.badania.model.LabDevice;

public interface LabDeviceRepository extends JpaRepository<LabDevice, Long> {
}
