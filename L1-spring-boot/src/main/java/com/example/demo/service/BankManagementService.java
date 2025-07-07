package com.example.demo.service;

import com.example.demo.entity.User;

public interface BankManagementService {

    User registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);
}
