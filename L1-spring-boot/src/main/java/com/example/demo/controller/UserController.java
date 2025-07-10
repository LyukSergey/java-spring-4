package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.BankManagementService;
import com.example.demo.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@RequestMapping("/users") // Базовий шлях для всіх ендпоінтів

public class UserController {

    private final UserService userService;
    private final BankManagementService bankManagementService;

    public UserController(UserService userService, BankManagementService bankManagementService) {
        this.userService = userService;
        this.bankManagementService = bankManagementService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        bankManagementService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
