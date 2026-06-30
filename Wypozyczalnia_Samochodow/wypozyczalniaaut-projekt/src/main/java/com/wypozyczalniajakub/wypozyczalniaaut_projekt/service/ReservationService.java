package com.wypozyczalniajakub.wypozyczalniaaut_projekt.service;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.AppUser;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Car;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.AppUserRepository;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.CarRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.ReservationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {
        private final AppUserService appUserService;
        private final ReservationRepository reservationRepository;
        private final CarRepository carRepository;
        private final AppUserRepository appUserRepository;

        

        private record DiscountThreshold(long days, BigDecimal multiplier) { // Dla ładnego opisywania danych
        }

        // Walidacja dat dla funkcji obliczania kosztu wynajmu
        private void validateDates(LocalDate startDate, LocalDate endDate) {
                Optional.ofNullable(startDate)
                                .filter(date -> endDate != null)
                                .filter(date -> !date.isBefore(LocalDate.now()))
                                .filter(date -> !date.isAfter(endDate))
                                .orElseThrow(() -> new IllegalArgumentException("Wrong reservation dates."));
        }

        @SuppressWarnings("null")
        public BigDecimal sumRentPrice(LocalDate startDate, LocalDate endDate, Long carId, Long userId) {
                validateDates(startDate, endDate);
                Car car = carRepository.findById(carId)
                                .orElseThrow(() -> new RuntimeException("Car not found"));
                AppUser user = appUserRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

                BigDecimal basePrice = car.getPriceFor24h()
                                .multiply(BigDecimal.valueOf(days));

                BigDecimal priceWithYoungDriverFee = Optional.of(user)
                                .filter(appUser -> appUser.getAge() < 25)
                                .map(appUser -> basePrice.multiply(new BigDecimal("1.20")))
                                .orElse(basePrice);

                BigDecimal discountMultiplier = Stream.of(
                                new DiscountThreshold(14, new BigDecimal("0.85")),
                                new DiscountThreshold(7, new BigDecimal("0.90")),
                                new DiscountThreshold(3, new BigDecimal("0.95")))
                                .filter(discount -> days >= discount.days())
                                .findFirst()
                                .map(DiscountThreshold::multiplier)
                                .orElse(BigDecimal.ONE);

                return priceWithYoungDriverFee.multiply(discountMultiplier);
        }

        public Reservation createReservation(Long carId, LocalDate startDate, LocalDate endDate) {
                validateDates(startDate, endDate);
                Car car = carRepository.findById(carId)
                                .orElseThrow(() -> new RuntimeException("Car not found")); 
                AppUser user = appUserService.getLoggedInUser();
                Optional.of(reservationRepository.existsOverlappingReservation(carId, startDate, endDate))
                .filter(isReserved -> !isReserved) // zmieniamy wynik aby filter przepuscil niezarezerwowane
                .orElseThrow(() -> new IllegalStateException("Car is already reserved in this time range."));
                
                Reservation reservation = new Reservation();
                reservation.setCar(car);
                reservation.setUser(user);
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                reservation.setFinalCost(sumRentPrice(startDate, endDate, carId, user.getId()));
                
                return reservationRepository.save(reservation);
        }

        public List<Reservation> getLoggedInUserReservations() {
                AppUser user = appUserService.getLoggedInUser();
                return reservationRepository.findByUserId(user.getId());
        }

        public List<Reservation> getAllReservations() {
                return reservationRepository.findAll();
        }
}
