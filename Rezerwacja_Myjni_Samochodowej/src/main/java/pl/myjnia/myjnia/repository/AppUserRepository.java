package pl.myjnia.myjnia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.myjnia.myjnia.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
