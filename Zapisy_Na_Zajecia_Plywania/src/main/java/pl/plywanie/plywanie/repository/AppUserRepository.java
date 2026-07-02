package pl.plywanie.plywanie.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.plywanie.plywanie.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
