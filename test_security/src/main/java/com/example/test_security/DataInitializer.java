package com.example.test_security;

import com.example.test_security.entity.User;
import com.example.test_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("password")); // Шифруємо пароль
        adminUser.setRole("ROLE_ADMIN"); // Ролі мають починатися з "ROLE_"
        userRepository.save(adminUser);
    }
}