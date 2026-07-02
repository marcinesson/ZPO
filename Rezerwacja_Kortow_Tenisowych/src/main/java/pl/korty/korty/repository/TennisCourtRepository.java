package pl.korty.korty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.korty.korty.model.TennisCourt;

public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
}
