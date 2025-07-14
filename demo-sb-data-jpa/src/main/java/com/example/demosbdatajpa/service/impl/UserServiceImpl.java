package com.example.demosbdatajpa.service.impl;

import com.example.demosbdatajpa.dto.UserDto;
import com.example.demosbdatajpa.entity.User;
import com.example.demosbdatajpa.mapper.UserMapper;
import com.example.demosbdatajpa.repository.UserRepository;
import com.example.demosbdatajpa.service.UserService;
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
