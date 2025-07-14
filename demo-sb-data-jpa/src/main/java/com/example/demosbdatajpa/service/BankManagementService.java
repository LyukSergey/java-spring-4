package com.example.demosbdatajpa.service;

import com.example.demosbdatajpa.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);
    Optional<UserDto> findUserWithLongestSurname(Long bankId, boolean withStream);

}
