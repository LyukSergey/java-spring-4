package com.example.bankapi.controller;

import com.example.bankapi.entity.User;
import com.example.bankapi.service.BankManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankController {
    private final BankManagementService bankService;

    @GetMapping("/banks/{bankId}/users")
    public ResponseEntity<List<User>> getUsersByBank(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getUsersByBank(bankId));
    }

    @PostMapping("/users/register")
    public User registerUser(@RequestBody RegisterUserRequest request) {
        return bankService.registerNewUser(
            request.getName(), request.getSurname(), request.getBankId()
        );
    }
}
