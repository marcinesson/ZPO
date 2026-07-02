package pl.agd.agd.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.agd.agd.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
