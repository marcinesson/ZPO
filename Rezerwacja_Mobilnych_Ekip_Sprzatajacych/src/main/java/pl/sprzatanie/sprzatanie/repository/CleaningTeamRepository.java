package pl.sprzatanie.sprzatanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sprzatanie.sprzatanie.model.CleaningTeam;

public interface CleaningTeamRepository extends JpaRepository<CleaningTeam, Long> {
}
