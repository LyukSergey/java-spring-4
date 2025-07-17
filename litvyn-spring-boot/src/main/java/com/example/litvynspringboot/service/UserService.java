package com.example.litvynspringboot.service;

import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);
}
