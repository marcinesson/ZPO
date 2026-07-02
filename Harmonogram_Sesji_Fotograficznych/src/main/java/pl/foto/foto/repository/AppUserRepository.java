package pl.foto.foto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.foto.foto.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
