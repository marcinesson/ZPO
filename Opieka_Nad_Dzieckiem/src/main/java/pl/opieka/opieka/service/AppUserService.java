package pl.opieka.opieka.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.opieka.opieka.model.AppUser;
import pl.opieka.opieka.repository.AppUserRepository;

@Service
@RequiredArgsConstructor
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
}
