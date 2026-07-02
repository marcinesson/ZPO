package com.example.wypozyczalnia_samochodow.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingAlgorithmTest {

    PricingAlgorithm algo = new PricingAlgorithm();

    @Test
    void shouldApply10PercentDiscountFor4Days() {
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(4);

        BigDecimal total = algo.calculateTotalCost(price, start, end, false);

        assertEquals(0, BigDecimal.valueOf(360.0).compareTo(total));
    }
}