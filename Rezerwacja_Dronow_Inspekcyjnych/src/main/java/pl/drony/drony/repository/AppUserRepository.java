package pl.drony.drony.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.drony.drony.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
