package pl.hotel.hotel.repository;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.hotel.hotel.model.*;
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
    List<AppUser> findByRole(UserRole role);
}
