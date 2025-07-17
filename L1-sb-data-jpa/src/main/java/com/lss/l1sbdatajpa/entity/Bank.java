package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity // 1. Це клас, що відповідає таблиці в БД
@Table(name = "bank") // 2. Явнa назва таблиці
@Getter
@Setter
public class Bank {
    @Id // 3. Первинний ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. Генерація ID на боці БД
    private Long id;

    private String name;

    @Column(name = "total_amount")
    private double totalAmount;

    // 5. Зв'язок "Один до Багатьох"
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}