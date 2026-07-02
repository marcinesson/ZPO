package pl.stoliki.stoliki.service;
import java.util.List;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.stoliki.stoliki.model.*;
import pl.stoliki.stoliki.repository.AppUserRepository;
@Service @RequiredArgsConstructor @Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;
    public AppUser getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return java.util.Optional.ofNullable(authentication)
            .filter(Authentication::isAuthenticated)
            .filter(auth -> !"anonymousUser".equals(auth.getPrincipal()))
            .map(Authentication::getName)
            .flatMap(appUserRepository::findByLogin)
            .orElseThrow(() -> new RuntimeException("No user is logged in."));
    }
    public List<AppUser> getUsersByRole(UserRole role) {
        return appUserRepository.findByRole(role);
    }
}
