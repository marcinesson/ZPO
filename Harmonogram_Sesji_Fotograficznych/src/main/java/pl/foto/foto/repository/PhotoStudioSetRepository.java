package pl.foto.foto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.foto.foto.model.PhotoStudioSet;

public interface PhotoStudioSetRepository extends JpaRepository<PhotoStudioSet, Long> {
}
