package pl.kurierzy.kurierzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurierzy.kurierzy.model.CourierVehicle;

public interface CourierVehicleRepository extends JpaRepository<CourierVehicle, Long> {
}
