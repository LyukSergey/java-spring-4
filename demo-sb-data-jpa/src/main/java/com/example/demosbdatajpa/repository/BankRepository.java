package com.example.demosbdatajpa.repository;

import com.example.demosbdatajpa.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
