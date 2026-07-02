package pl.kosmetyczka.kosmetyczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kosmetyczka.kosmetyczka.model.BeautyStation;

public interface BeautyStationRepository extends JpaRepository<BeautyStation, Long> {
}
