package pl.telefony.telefony.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.telefony.telefony.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
