package com.horovyak.teststarter.controller;

import com.horovyak.teststarter.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/do") // Базовий шлях для всіх ендпоінтів
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping
    public ResponseEntity<Void> getUsers() {
        myService.doWork();
        return ResponseEntity.noContent().build();
    }

}
