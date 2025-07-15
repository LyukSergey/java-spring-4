package com.udemy.vashchykdataprojectjpa.service.impl;

import com.udemy.vashchykdataprojectjpa.dto.UserDto;
import com.udemy.vashchykdataprojectjpa.entity.User;
import com.udemy.vashchykdataprojectjpa.mapper.UserMapper;
import com.udemy.vashchykdataprojectjpa.repository.UserRepository;
import com.udemy.vashchykdataprojectjpa.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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