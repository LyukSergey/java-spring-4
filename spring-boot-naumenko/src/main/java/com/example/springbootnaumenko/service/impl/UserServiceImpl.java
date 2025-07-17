package com.example.springbootnaumenko.service.impl;

import com.example.springbootnaumenko.dto.UserDto;
import com.example.springbootnaumenko.entity.User;
import com.example.springbootnaumenko.mapper.UserMapper;
import com.example.springbootnaumenko.repository.UserRepository;
import com.example.springbootnaumenko.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        final List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }
}
