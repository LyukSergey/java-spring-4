package com.example.demo.service;

import com.example.demo.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface UserService {

    List<UserDto> getAllUsers();

    // ✅ ДОДАНО ДЛЯ ЗАВДАННЯ
    void deleteUser(Long userId);
}
