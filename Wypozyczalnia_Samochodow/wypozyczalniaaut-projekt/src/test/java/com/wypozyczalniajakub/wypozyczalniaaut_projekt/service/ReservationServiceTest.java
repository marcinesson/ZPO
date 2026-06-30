package com.wypozyczalniajakub.wypozyczalniaaut_projekt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.AppUser;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Car;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.AppUserRepository;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.CarRepository;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldCalculateOneDayRentPriceForAdultDriver() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate;
        mockCarPrice("100.00");
        mockUserAge(30);

        BigDecimal result = reservationService.sumRentPrice(startDate, endDate, 1L, 1L);

        assertBigDecimalEquals("100.00", result);
    }

    @Test
    void shouldAddYoungDriverFee() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate;
        mockCarPrice("100.00");
        mockUserAge(20);

        BigDecimal result = reservationService.sumRentPrice(startDate, endDate, 1L, 1L);

        assertBigDecimalEquals("120.00", result);
    }

    @Test
    void shouldApplyThreeDayDiscount() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(2);
        mockCarPrice("100.00");
        mockUserAge(30);

        BigDecimal result = reservationService.sumRentPrice(startDate, endDate, 1L, 1L);

        assertBigDecimalEquals("285.00", result);
    }

    @Test
    void shouldApplySevenDayDiscountWithYoungDriverFee() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(6);
        mockCarPrice("100.00");
        mockUserAge(20);

        BigDecimal result = reservationService.sumRentPrice(startDate, endDate, 1L, 1L);

        assertBigDecimalEquals("756.00", result);
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = LocalDate.now().plusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> reservationService.sumRentPrice(startDate, endDate, 1L, 1L));
    }

    private void mockCarPrice(String price) {
        Car car = new Car();
        car.setId(1L);
        car.setPriceFor24h(new BigDecimal(price));

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
    }

    private void mockUserAge(int age) {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setAge(age);

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    private void assertBigDecimalEquals(String expected, BigDecimal actual) {
        assertEquals(0, actual.compareTo(new BigDecimal(expected)));
    }
}
