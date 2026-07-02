package pl.weterynarz.weterynarz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.weterynarz.weterynarz.model.AppUser;
import pl.weterynarz.weterynarz.model.UserRole;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);

    List<AppUser> findByRole(UserRole role);
}
