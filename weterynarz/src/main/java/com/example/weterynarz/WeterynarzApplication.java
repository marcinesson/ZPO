package com.example.weterynarz;

import com.example.weterynarz.model.Vet;
import com.example.weterynarz.repository.VetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
public class WeterynarzApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeterynarzApplication.class, args);
    }

    // W 100% funkcyjne ładowanie danych - ZERO ifów!
    @Bean
    public CommandLineRunner initData(VetRepository repo) {
        return args -> Optional.of(repo.count())
                // Przepuszczamy dalej tylko, gdy w bazie nie ma lekarzy (zastępuje "if")
                .filter(count -> count == 0)
                .ifPresent(count -> {
                    Vet v1 = new Vet();
                    v1.setFullName("Dr. Jan Kowalski");
                    v1.setSpecialization("Chirurg");

                    Vet v2 = new Vet();
                    v2.setFullName("Dr. Anna Nowak");
                    v2.setSpecialization("Kardiolog");

                    // Zapisujemy funkcyjnie przez strumień
                    Stream.of(v1, v2).forEach(repo::save);
                });
    }
}