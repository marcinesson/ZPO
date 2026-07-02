package pl.bowling.bowling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bowling.bowling.model.BowlingLane;

public interface BowlingLaneRepository extends JpaRepository<BowlingLane, Long> {
}
