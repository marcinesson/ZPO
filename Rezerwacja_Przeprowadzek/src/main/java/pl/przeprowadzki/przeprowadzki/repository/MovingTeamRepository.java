package pl.przeprowadzki.przeprowadzki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przeprowadzki.przeprowadzki.model.MovingTeam;

public interface MovingTeamRepository extends JpaRepository<MovingTeam, Long> {
}
