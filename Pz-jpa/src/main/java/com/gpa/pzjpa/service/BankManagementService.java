package com.gpa.pzjpa.service;

import com.gpa.pzjpa.dto.UserDto;

import java.util.List;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);

    UserDto getMaxSurnameLength(Long bankId, boolean withStream);
}