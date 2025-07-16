package com.example.jpanaumenko.service.impl;

import com.example.jpanaumenko.dto.UserDto;
import com.example.jpanaumenko.entity.User;
import com.example.jpanaumenko.mapper.UserMapper;
import com.example.jpanaumenko.repository.UserRepository;
import com.example.jpanaumenko.service.UserService;
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
