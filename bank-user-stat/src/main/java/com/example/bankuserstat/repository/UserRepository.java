package com.example.bankuserstat.repository;

import com.example.bankuserstat.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBankId(Long bankId);

    @Query(value = "SELECT * FROM users WHERE bank_id = :bankId ORDER BY LENGTH(surname) DESC LIMIT 1", nativeQuery = true)
    User findUserWithLongestSurnameByBank(@Param("bankId") Long bankId);

    @Query(value = "SELECT b.name AS bank_name, COUNT(u.id) AS user_count FROM users u JOIN bank b ON u.bank_id = b.id GROUP BY b.name ORDER BY user_count DESC", nativeQuery = true)
    List<Object[]> getBankUserStats();
}
