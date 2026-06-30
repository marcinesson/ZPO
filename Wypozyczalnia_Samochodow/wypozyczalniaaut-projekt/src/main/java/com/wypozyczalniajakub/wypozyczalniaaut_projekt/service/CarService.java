package com.wypozyczalniajakub.wypozyczalniaaut_projekt.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Car;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.CarRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;

    @Transactional
    public void deleteOrDisableCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No car found with an id: " + id));

        // Sprawdzenie przyszłych rezerwacji
        car.getReservations().stream()
                .filter(reservation -> !reservation.getEndDate().isBefore(LocalDate.now()))
                .findAny()
                .ifPresent(reservation -> {
                    throw new IllegalStateException("Car has an active or planned reservation, can't delete it.");
                });

        // Sprawdzenie czy lista rezerwacji nie jest pusta.
        Optional.of(car.getReservations())
                .filter(reservations -> !reservations.isEmpty())
                .ifPresentOrElse(
                        // Lista nie jest pusta = auto ma historie - usstawiamy na nieaktywne
                        reservations -> car.setAvailable(false),

                        // Jesli filter odrzuci, znaczy ze lista pusta wiec usuwamy.
                        () -> carRepository.delete(car));
    }

    @Transactional
    public Car updateCarPrice(Long id, BigDecimal newPrice) {
        Optional.ofNullable(newPrice)
        .filter(price -> price.compareTo(BigDecimal.ZERO) > 0) // Przy porownywaniu obiektów używamy compareTo, jesli zwroci -1 to jest mniejsze, jesli zwroci 0 to jest równe, a jeśli zwróci 1 to jest większe.
        .orElseThrow(() -> new IllegalArgumentException("Price must be greater than zero."));
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No car found with an id: " + id));

        car.setPriceFor24h(newPrice);
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> getAvailableCars(LocalDate starDate, LocalDate endDate) {
        return carRepository.findAvailableCars(starDate, endDate);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't find a car with an id: " + id));
    }

    public Car addNewCar(Car car) {
        return carRepository.save(car);
    }
}
