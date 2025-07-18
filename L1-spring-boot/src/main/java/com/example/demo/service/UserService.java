package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    @Transactional
    public default void deleteUser(Long userId) {
        UserRepository userRepository = null;
        userRepository.deleteById(userId);
    }
}
