package com.example.weterynarz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String specialization;
}