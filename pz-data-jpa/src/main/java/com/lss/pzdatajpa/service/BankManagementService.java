package com.lss.pzdatajpa.service;

import com.lss.pzdatajpa.dto.UserDto;

import java.util.List;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);

    UserDto getMaxSurnameLength(Long bankId, boolean withStream);
}
