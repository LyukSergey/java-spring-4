package com.example.litvynspringboot.service;


import com.example.demo.dto.UserDto;

import java.util.List;

public interface BankManagementService {
    UserDto registerNewUser(String name, String surname, Long bankId);
    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);
    List<UserDto> getUsersByBankId(Long bankId);
}

