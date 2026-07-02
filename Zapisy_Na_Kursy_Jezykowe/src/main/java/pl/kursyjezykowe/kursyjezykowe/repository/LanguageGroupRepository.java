package pl.kursyjezykowe.kursyjezykowe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kursyjezykowe.kursyjezykowe.model.LanguageGroup;

public interface LanguageGroupRepository extends JpaRepository<LanguageGroup, Long> {
}
