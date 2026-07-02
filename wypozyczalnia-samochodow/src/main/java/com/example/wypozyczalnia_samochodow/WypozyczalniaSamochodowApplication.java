package com.example.wypozyczalnia_samochodow;

import com.example.wypozyczalnia_samochodow.model.Car;
import com.example.wypozyczalnia_samochodow.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
public class WypozyczalniaSamochodowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WypozyczalniaSamochodowApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(CarRepository carRepository) {
        return args -> Optional.of(carRepository.count())
                .filter(count -> count == 0)
                .ifPresent(count -> {
                    Car car1 = new Car();
                    car1.setBrand("Toyota"); // <-- POPRAWIONE TUTAJ
                    car1.setModel("Corolla");
                    car1.setPricePerDay(new BigDecimal("150.00"));

                    Car car2 = new Car();
                    car2.setBrand("BMW"); // <-- POPRAWIONE TUTAJ
                    car2.setModel("Seria 3");
                    car2.setPricePerDay(new BigDecimal("300.00"));

                    Car car3 = new Car();
                    car3.setBrand("Skoda"); // <-- POPRAWIONE TUTAJ
                    car3.setModel("Octavia");
                    car3.setPricePerDay(new BigDecimal("120.00"));

                    Stream.of(car1, car2, car3).forEach(carRepository::save);
                });
    }
}