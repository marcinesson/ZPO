package pl.symulatory.symulatory.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.symulatory.symulatory.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
