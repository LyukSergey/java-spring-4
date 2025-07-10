package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.service.BankManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks") // Базовий шлях для всіх ендпоінтів
@RequiredArgsConstructor
public class BankController {

    private final BankManagementService bankService;

    // Ендпоінт для реєстрації користувача
    @PostMapping("/{bank_id}/users")
    public ResponseEntity<UserDto> registerUser(@PathVariable("bank_id") Long bankId, @RequestBody UserRegistrationDto request) {
        final UserDto userDto = bankService.registerNewUser(request.getName(), request.getSurname(), bankId);
        return ResponseEntity.ok(userDto);
    }

    // Ендпоінт для видалення користувача
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        bankService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/banks/{bankId}/users")
    public ResponseEntity<List<UserDto>> getUsersByBank(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getUsersByBank(bankId));
    }

}