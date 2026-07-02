package pl.plywanie.plywanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.plywanie.plywanie.model.SwimmingGroup;

public interface SwimmingGroupRepository extends JpaRepository<SwimmingGroup, Long> {
}
