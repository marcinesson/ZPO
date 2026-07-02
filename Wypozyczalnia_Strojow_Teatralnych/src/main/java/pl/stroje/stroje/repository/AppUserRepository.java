package pl.stroje.stroje.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.stroje.stroje.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
