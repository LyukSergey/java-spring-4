package com.example.litvynspringboot.service.impl;

import com.example.litvynspringboot.dto.UserDto;
import com.example.litvynspringboot.entity.Bank;
import com.example.litvynspringboot.entity.User;
import com.example.litvynspringboot.mapper.UserMapper;
import com.example.litvynspringboot.repository.BankRepository;
import com.example.litvynspringboot.repository.UserRepository;
import com.example.litvynspringboot.service.BankManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto registerNewUser(String name, String surname, Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow();
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
