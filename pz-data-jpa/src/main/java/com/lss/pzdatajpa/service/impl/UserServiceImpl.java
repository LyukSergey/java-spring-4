package com.lss.pzdatajpa.service.impl;

import com.lss.pzdatajpa.dto.UserDto;
import com.lss.pzdatajpa.entity.User;
import com.lss.pzdatajpa.mapper.UserMapper;
import com.lss.pzdatajpa.repository.UserRepository;
import com.lss.pzdatajpa.service.UserService;
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
