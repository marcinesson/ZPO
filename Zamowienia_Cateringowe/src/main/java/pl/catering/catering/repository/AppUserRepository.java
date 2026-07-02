package pl.catering.catering.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.catering.catering.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
