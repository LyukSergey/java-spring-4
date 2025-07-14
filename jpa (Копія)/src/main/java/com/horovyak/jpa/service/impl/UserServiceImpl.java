package com.horovyak.jpa.service.impl;

import com.horovyak.jpa.dto.UserDto;
import com.horovyak.jpa.entity.User;
import com.horovyak.jpa.mapper.UserMapper;
import com.horovyak.jpa.repository.UserRepository;
import com.horovyak.jpa.service.UserService;
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
