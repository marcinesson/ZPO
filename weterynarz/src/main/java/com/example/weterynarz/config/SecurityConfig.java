package com.example.weterynarz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Tylko szef (ADMIN) widzi raporty finansowe
                .requestMatchers("/revenue/**").hasRole("ADMIN")
                // Zwykłe widoki i panel wymagają tylko bycia zalogowanym
                .requestMatchers("/panel/**", "/book/**").authenticated()
                // Reszta stron jest publiczna
                .anyRequest().permitAll()
        ).formLogin(login -> login.defaultSuccessUrl("/panel", true));

        // Zabezpieczenie przed błędem 403 przy wylogowywaniu
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    // TWORZYMY STAŁYCH UŻYTKOWNIKÓW
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // 1. Zwykły pracownik (widzi tylko swoje wizyty)
        UserDetails pracownik = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        // 2. Szef kliniki (widzi wszystko i ma dostęp do finansów)
        UserDetails szef = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(pracownik, szef);
    }

    // MECHANIZM SZYFROWANIA HASEŁ
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}