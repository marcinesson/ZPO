package pl.opieka.opieka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.opieka.opieka.model.Babysitter;

public interface BabysitterRepository extends JpaRepository<Babysitter, Long> {
}
