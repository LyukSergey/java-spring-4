package com.example.jpanaumenko.service;
import com.example.jpanaumenko.dto.UserDto;
import java.util.Optional;
import java.util.List;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);

    Optional <UserDto> getUserWithLongestSurnameFromDb(Long bankId);
    Optional <UserDto> getUserWithLongestSurname(Long bankId, boolean withStream);


}
