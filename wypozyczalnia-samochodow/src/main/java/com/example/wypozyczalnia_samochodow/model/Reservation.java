package com.example.wypozyczalnia_samochodow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToOne
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean youngDriver;
    private BigDecimal totalCost;
}