package pl.korty.korty.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.korty.korty.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
