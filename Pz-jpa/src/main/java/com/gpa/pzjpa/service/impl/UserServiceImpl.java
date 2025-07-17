package com.gpa.pzjpa.service.impl;

import com.gpa.pzjpa.dto.UserDto;
import com.gpa.pzjpa.entity.User;
import com.gpa.pzjpa.mapper.UserMapper;
import com.gpa.pzjpa.repository.UserRepository;
import com.gpa.pzjpa.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        final List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }
}