package com.example.bankapi.repository;

import com.example.bankapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBankId(Long bankId);
}
