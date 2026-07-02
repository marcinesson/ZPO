package pl.robotyka.robotyka.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.robotyka.robotyka.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
