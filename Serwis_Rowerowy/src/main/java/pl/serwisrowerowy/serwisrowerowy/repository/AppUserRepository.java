package pl.serwisrowerowy.serwisrowerowy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.serwisrowerowy.serwisrowerowy.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
