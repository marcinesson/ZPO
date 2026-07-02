package pl.robotyka.robotyka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robotyka.robotyka.model.RoboticsLab;

public interface RoboticsLabRepository extends JpaRepository<RoboticsLab, Long> {
}
