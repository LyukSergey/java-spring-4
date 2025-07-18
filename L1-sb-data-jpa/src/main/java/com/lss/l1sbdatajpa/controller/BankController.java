package com.lss.l1sbdatajpa.controller;

import com.lss.l1sbdatajpa.dto.UserDto;
import com.lss.l1sbdatajpa.dto.UserRegistrationDto;
import com.lss.l1sbdatajpa.service.BankManagementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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