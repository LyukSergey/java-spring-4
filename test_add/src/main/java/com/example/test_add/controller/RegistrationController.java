package com.example.test_add.controller;

import com.example.test_add.dto.RegistrationRequest;
import com.example.test_add.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        registrationService.registerNewUser(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.ok("Користувача успішно зареєстровано! Тепер ви можете увійти.");
    }

}
