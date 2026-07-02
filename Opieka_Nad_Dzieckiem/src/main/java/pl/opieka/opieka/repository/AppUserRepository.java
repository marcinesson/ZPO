package pl.opieka.opieka.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.opieka.opieka.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
