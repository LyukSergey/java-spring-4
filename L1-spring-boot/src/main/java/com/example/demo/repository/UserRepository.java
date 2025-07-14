package com.example.demo.repository;

import com.example.demo.entity.User;
<<<<<<< HEAD
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByBankId(Long bankId);

=======

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBankId(Long bankId);
>>>>>>> 1476f90535505e67bbf6f24bf48a68f14e53bcd9
}
