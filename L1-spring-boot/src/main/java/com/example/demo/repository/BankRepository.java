package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<com.example.demo.entity.Bank, Long> {

}
