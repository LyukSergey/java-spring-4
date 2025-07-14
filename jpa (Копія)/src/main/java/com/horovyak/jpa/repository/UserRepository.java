package com.horovyak.jpa.repository;

import com.horovyak.jpa.entity.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByBankId(Long bankId);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findUsersByNameHQL(@Param("name") String name);

    @Query(value = "SELECT * FROM users u WHERE u.name = :name", nativeQuery = true)
    List<User> findUsersByNameNativeSQL(@Param("name") String name);


}
