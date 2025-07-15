package com.udemy.vashchykdataprojectjpa.service;

import com.udemy.vashchykdataprojectjpa.dto.UserDto;
import java.util.List;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);
}
