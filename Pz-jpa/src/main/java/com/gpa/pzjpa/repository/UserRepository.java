package com.gpa.pzjpa.repository;

import com.gpa.pzjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByBankId(Long bankId);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findUsersByNameHQL(@Param("name") String name);

    @Query(value = "SELECT * FROM users u WHERE u.name = :name", nativeQuery = true)
    List<User> findUsersByNameNativeSQL(@Param("name") String name);

    @Query(value = "SELECT * FROM users WHERE bank_id = :bankId ORDER BY LENGTH(surname) DESC LIMIT 1", nativeQuery = true)
    Optional<User> findTopUserWithLongestSurnameByBankId(@Param("bankId") Long bankId);

}