package com.lss.l1sbdatajpa.service.impl;

import com.lss.l1sbdatajpa.dto.UserDto;
import com.lss.l1sbdatajpa.entity.User;
import com.lss.l1sbdatajpa.mapper.UserMapper;
import com.lss.l1sbdatajpa.repository.UserRepository;
import com.lss.l1sbdatajpa.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
