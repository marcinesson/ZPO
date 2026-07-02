package pl.stroje.stroje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.stroje.stroje.model.Costume;

public interface CostumeRepository extends JpaRepository<Costume, Long> {
}
