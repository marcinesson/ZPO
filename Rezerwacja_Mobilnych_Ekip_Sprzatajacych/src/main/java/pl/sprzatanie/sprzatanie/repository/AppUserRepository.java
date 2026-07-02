package pl.sprzatanie.sprzatanie.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.sprzatanie.sprzatanie.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
