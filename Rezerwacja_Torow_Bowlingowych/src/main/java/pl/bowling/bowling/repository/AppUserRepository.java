package pl.bowling.bowling.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.bowling.bowling.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
