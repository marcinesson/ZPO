package pl.przeprowadzki.przeprowadzki.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.przeprowadzki.przeprowadzki.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
