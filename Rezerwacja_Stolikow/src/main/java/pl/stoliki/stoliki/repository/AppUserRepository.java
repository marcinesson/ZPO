package pl.stoliki.stoliki.repository;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.stoliki.stoliki.model.*;
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
    List<AppUser> findByRole(UserRole role);
}
