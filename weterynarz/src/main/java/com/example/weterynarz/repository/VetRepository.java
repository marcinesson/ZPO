package com.example.weterynarz.repository;

import com.example.weterynarz.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {}