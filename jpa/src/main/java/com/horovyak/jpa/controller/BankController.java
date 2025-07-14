package com.horovyak.jpa.controller;

import com.horovyak.jpa.dto.UserDto;
import com.horovyak.jpa.dto.UserRegistrationDto;
import com.horovyak.jpa.service.BankManagementService;
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

    @GetMapping("/{bankId}/users")
    public ResponseEntity<List<UserDto>> getUsersByBank(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getUsersByBank(bankId));
    }

    @GetMapping("/{bankId}/users/max-sourname-length")
    public ResponseEntity<UserDto> getUserWithLongestSurname(
            @PathVariable Long bankId,
            @RequestParam(name = "with-stream", defaultValue = "false") boolean withStream) {

        long start = System.currentTimeMillis();
        UserDto result;

        if (withStream) {
            List<UserDto> users = bankService.getUsersByBank(bankId);

            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            result = users.stream()
                    .max((u1, u2) -> Integer.compare(u1.getSurname().length(), u2.getSurname().length()))
                    .orElseThrow();

        } else {
            result = bankService.getUserWithLongestSurnameFromDb(bankId)
                    .orElse(null);

            if (result == null) {
                return ResponseEntity.notFound().build();
            }
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("Execution time (" + (withStream ? "stream" : "db") + "): " + duration + " ms");

        return ResponseEntity.ok(result);
    }

}