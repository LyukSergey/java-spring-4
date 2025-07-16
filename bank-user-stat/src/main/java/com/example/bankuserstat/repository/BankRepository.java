package com.example.bankuserstat.repository;

import com.example.bankuserstat.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
