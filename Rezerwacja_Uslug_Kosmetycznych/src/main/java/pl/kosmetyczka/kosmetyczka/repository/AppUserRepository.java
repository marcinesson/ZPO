package pl.kosmetyczka.kosmetyczka.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kosmetyczka.kosmetyczka.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
