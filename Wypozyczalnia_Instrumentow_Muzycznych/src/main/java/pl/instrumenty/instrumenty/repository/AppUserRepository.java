package pl.instrumenty.instrumenty.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.instrumenty.instrumenty.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
