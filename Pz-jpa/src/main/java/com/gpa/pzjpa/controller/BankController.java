package com.gpa.pzjpa.controller;

import com.gpa.pzjpa.dto.UserDto;
import com.gpa.pzjpa.dto.UserRegistrationDto;
import com.gpa.pzjpa.service.BankManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks") // Базовий шлях для всіх ендпоінтів

public class BankController {

    private final BankManagementService bankService;

    public BankController(BankManagementService bankService) {
        this.bankService = bankService;
    }

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

    @GetMapping("/{bankId}/users")
    public ResponseEntity<List<UserDto>> getUsersByBank(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getUsersByBank(bankId));
    }

    @GetMapping("/{bank-id}/users/max-surname-length")
    public ResponseEntity<UserDto> getMaxSurnameLength(@PathVariable(name = "bank-id") Long bankId,
                                                       @RequestParam(value = "with-stream", required = false, defaultValue = "false") Boolean withStream) {
        return ResponseEntity.ok(bankService.getMaxSurnameLength(bankId, withStream));
    }
}
