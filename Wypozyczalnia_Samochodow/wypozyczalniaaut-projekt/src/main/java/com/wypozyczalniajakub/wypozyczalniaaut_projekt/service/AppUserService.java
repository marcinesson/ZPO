package com.wypozyczalniajakub.wypozyczalniaaut_projekt.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.AppUser;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.AppUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @SuppressWarnings("null")
    public AppUser getLoggedInUser() {
        // Pobranie danych o zalogowanym użytkowniku
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return java.util.Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .filter(auth -> !"anonymousUser".equals(auth.getPrincipal()))
                .map(Authentication::getName)
                .flatMap(appUserRepository::findByLogin)
                .orElseThrow(() -> new RuntimeException("No user is logged in."));
    }
}
