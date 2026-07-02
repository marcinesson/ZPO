package pl.kurierzy.kurierzy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurierzy.kurierzy.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
