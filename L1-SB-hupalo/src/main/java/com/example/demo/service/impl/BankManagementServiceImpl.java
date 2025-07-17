package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Bank;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BankManagementService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto registerNewUser(String name, String surname, Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Банк не знайдено!"));
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setBank(bank);
        return userMapper.toDto(userRepository.save(newUser));
    }

    @Override
    @Transactional
    public List<UserDto> getUsersByBank(Long bankId) {
        final List<User> users = userRepository.findAllByBankId(bankId);
        return users.stream()
                .map(user -> userMapper.toDto(user))
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public List<UserDto> getUsersByBankId(Long bankId) {
        bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Банк не знайдено!"));
        List<User> users = userRepository.findAllByBankId(bankId);
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

}
