package pl.narzedzia.narzedzia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.narzedzia.narzedzia.model.ToolSet;

public interface ToolSetRepository extends JpaRepository<ToolSet, Long> {
}
