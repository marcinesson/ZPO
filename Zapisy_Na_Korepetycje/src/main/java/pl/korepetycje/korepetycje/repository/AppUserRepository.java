package pl.korepetycje.korepetycje.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.korepetycje.korepetycje.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
