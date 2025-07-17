package com.example.springbootnaumenko.service;

import com.example.springbootnaumenko.dto.UserDto;

import java.util.List;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);
}
