package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 1476f90535505e67bbf6f24bf48a68f14e53bcd9

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);
<<<<<<< HEAD

=======
    
>>>>>>> 1476f90535505e67bbf6f24bf48a68f14e53bcd9
    List<UserDto> getUsersByBank(Long bankId);
}
