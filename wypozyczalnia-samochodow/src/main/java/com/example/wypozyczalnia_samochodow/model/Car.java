package com.example.wypozyczalnia_samochodow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;


    private BigDecimal pricePerDay;
    private boolean isAutomatic;
    private boolean isAvailable;
}