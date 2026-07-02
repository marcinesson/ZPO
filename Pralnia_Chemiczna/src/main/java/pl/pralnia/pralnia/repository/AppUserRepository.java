package pl.pralnia.pralnia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.pralnia.pralnia.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
