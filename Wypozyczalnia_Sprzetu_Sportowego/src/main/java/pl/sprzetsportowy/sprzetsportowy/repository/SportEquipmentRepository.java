package pl.sprzetsportowy.sprzetsportowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sprzetsportowy.sprzetsportowy.model.SportEquipment;

public interface SportEquipmentRepository extends JpaRepository<SportEquipment, Long> {
}
