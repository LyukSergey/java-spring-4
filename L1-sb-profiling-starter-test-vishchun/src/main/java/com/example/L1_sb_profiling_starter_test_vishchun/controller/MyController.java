package com.example.L1_sb_profiling_starter_test_vishchun.controller;

import com.lss.l1sbprofilingstartertest.service.MyService;
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
