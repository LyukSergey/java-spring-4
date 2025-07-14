package com.horovyak.jpa.service;

import com.horovyak.jpa.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);
    Optional<UserDto> getUserWithLongestSurnameFromDb(Long bankId);

}
