package com.lss.l1springbootsecurity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;@RestController
@RequestMapping
public class DemoController {

    @GetMapping("/public")
    public String getPublicData() {
        return "Це публічні дані, доступні для всіх!";
    }

    @GetMapping("/secured")
    public String getSecuredData(@AuthenticationPrincipal Jwt jwt) {
        // @AuthenticationPrincipal дозволяє отримати дані про поточного користувача з токену
        return "Це захищені дані. Ви увійшли як: " + jwt.getSubject();
    }
}
