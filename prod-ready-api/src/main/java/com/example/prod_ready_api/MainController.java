package com.example.prod_ready_api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MainController {

    @GetMapping("/public")
    public String getPublicData() {
        return "Це публічні дані.";
    }

    @GetMapping("/private")
    public String getPrivateData(@AuthenticationPrincipal Jwt jwt) {
        return "Це приватні дані для користувача: " + jwt.getSubject();
    }
}