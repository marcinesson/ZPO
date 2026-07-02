package com.example.wypozyczalnia_samochodow.repository;

import com.example.wypozyczalnia_samochodow.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}