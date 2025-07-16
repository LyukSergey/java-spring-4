package com.example.bankapi.repository;

import com.example.bankapi.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    // ...existing code...
}