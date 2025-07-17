package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    // Зв'язок "Багато до Одного"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id") // Власник зв'язку
    private Bank bank;
}