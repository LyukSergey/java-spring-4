package com.example.demo.service;

import com.example.demo.dto.UserDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    @Transactional
    void deleteUser(Long userId);
}
