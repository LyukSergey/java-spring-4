package com.example.demo.service;

import com.example.demo.dto.UserDto;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);
}
