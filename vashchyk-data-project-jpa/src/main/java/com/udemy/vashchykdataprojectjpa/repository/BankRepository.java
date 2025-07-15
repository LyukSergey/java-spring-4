package com.udemy.vashchykdataprojectjpa.repository;

import com.udemy.vashchykdataprojectjpa.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

}