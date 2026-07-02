package pl.drukarki.drukarki.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.drukarki.drukarki.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
