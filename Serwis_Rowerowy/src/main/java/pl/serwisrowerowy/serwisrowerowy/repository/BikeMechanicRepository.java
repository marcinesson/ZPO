package pl.serwisrowerowy.serwisrowerowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.serwisrowerowy.serwisrowerowy.model.BikeMechanic;

public interface BikeMechanicRepository extends JpaRepository<BikeMechanic, Long> {
}
