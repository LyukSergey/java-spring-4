package com.example.springbootnaumenko.controller;

import com.example.springbootnaumenko.dto.UserDto;
import com.example.springbootnaumenko.mapper.UserMapper;
import com.example.springbootnaumenko.service.BankManagementService;
import com.example.springbootnaumenko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/users") // Базовий шлях для всіх ендпоінтів
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BankManagementService bankService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        bankService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
