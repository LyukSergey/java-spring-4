package com.example.bankapi.controller;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String surname;
    private Long bankId;
}
