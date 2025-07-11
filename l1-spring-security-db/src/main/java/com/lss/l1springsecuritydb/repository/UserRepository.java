package com.lss.l1springsecuritydb.repository;

import com.lss.l1springsecuritydb.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
