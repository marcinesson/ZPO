package pl.sprzetsportowy.sprzetsportowy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.sprzetsportowy.sprzetsportowy.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
