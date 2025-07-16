package com.example.jpanaumenko.service.impl;

import com.example.jpanaumenko.dto.UserDto;
import com.example.jpanaumenko.entity.Bank;
import com.example.jpanaumenko.entity.User;
import com.example.jpanaumenko.mapper.UserMapper;
import com.example.jpanaumenko.repository.BankRepository;
import com.example.jpanaumenko.repository.UserRepository;
import com.example.jpanaumenko.service.BankManagementService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;


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
                .orElseThrow(() -> new RuntimeException("Банк не знайдено!"));
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setBank(bank);
        return userMapper.toDto(userRepository.save(newUser));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserWithLongestSurnameFromDb(Long bankId) {
        long before = System.nanoTime();

        Optional<UserDto> result = userRepository
                .findTopUserWithLongestSurnameByBankId(bankId)
                .map(userMapper::toDto);

        long after = System.nanoTime();
        System.out.printf("Execution time (NATIVE QUERY): %.2f ms%n", (after - before) / 1_000_000.0);

        return result;
    }
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserWithLongestSurname(Long bankId, boolean withStream) {
        long before = System.currentTimeMillis();

        User result = withStream
                ? userRepository.findAllByBankId(bankId).stream()
                .max(Comparator.comparingInt(u -> u.getSurname().length()))
                .orElseThrow(() -> new RuntimeException("No users found"))

                : userRepository.findTopUserWithLongestSurnameByBankId(bankId)
                .orElseThrow(() -> new RuntimeException("No users found"));

        long after = System.currentTimeMillis();
        System.out.printf("Execution time (%s): %.2f ms%n",
                withStream ? "STREAM" : "NATIVE QUERY", (after - before) * 1.0);

        return Optional.ofNullable(userMapper.toDto(result));
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
}
