package pl.badania.badania.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.badania.badania.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
