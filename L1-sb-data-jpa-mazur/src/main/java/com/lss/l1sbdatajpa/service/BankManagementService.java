package com.lss.l1sbdatajpa.service;

import com.lss.l1sbdatajpa.dto.UserDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);

    @Transactional
    UserDto getMaxSurnameLength(Long bankId, boolean withStream);
}
