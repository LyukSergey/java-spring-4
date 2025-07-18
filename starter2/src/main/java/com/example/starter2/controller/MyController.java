package com.example.starter2.controller;

import com.example.starter2.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/do") // Базовий шлях для всіх ендпоінтів
public class MyController {

    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping
    public ResponseEntity<String> doWork() {
        myService.doWork();
        return ResponseEntity.ok("Операція виконана успішно");
    }

    @GetMapping("/process/{text}/{count}")
    public ResponseEntity<String> processText(
            @PathVariable("text") String text,
            @PathVariable("count") int count) {
        String result = myService.processText(text, count);
        return ResponseEntity.ok(result);
    }
}
