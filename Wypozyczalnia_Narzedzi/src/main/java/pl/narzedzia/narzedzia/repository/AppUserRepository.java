package pl.narzedzia.narzedzia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.narzedzia.narzedzia.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
