package pl.catering.catering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.catering.catering.model.CateringTeam;

public interface CateringTeamRepository extends JpaRepository<CateringTeam, Long> {
}
