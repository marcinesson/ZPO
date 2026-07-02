package com.example.wypozyczalnia_samochodow.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PricingAlgorithm {

    // 100% Funkcyjny algorytm - ZERO instrukcji "if", "else" czy "for"
    public BigDecimal calculateTotalCost(BigDecimal pricePerDay, LocalDate start, LocalDate end, boolean isYoungDriver) {

        // 1. Zabezpieczenie przed ujemnymi dniami bez użycia "if"
        long days = Math.max(1L, ChronoUnit.DAYS.between(start, end));
        BigDecimal basePrice = pricePerDay.multiply(BigDecimal.valueOf(days));

        // 2. Funkcyjne mapowanie zniżek (zastępuje if / else if)
        BigDecimal discountMultiplier = Optional.of(days)
                .filter(d -> d >= 7).map(d -> new BigDecimal("0.8"))
                .orElseGet(() -> Optional.of(days)
                        .filter(d -> d >= 3).map(d -> new BigDecimal("0.9"))
                        .orElse(BigDecimal.ONE)); // domyślnie mnożnik 1.0 (brak zniżki)

        // 3. Funkcyjna dopłata dla młodego kierowcy (zastępuje if)
        BigDecimal ageMultiplier = Optional.of(isYoungDriver)
                .filter(Boolean::booleanValue)
                .map(b -> new BigDecimal("1.2"))
                .orElse(BigDecimal.ONE); // domyślnie mnożnik 1.0 (brak dopłaty)

        // 4. Strumieniowe wymnożenie wszystkiego! (Baza * Zniżka * Dopłata)
        return Stream.of(basePrice, discountMultiplier, ageMultiplier)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }
}