package com.example.demo.repository;

import com.example.demo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBankId(Long bankId);
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findUsersByNameHQL(@Param("name") String name);
}