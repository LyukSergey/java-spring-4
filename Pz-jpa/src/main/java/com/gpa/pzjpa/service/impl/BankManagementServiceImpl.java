package com.gpa.pzjpa.service.impl;


import com.gpa.pzjpa.dto.UserDto;
import com.gpa.pzjpa.entity.Bank;
import com.gpa.pzjpa.entity.User;
import com.gpa.pzjpa.mapper.UserMapper;
import com.gpa.pzjpa.repository.BankRepository;
import com.gpa.pzjpa.repository.UserRepository;
import com.gpa.pzjpa.service.BankManagementService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public BankManagementServiceImpl(BankRepository bankRepository, UserRepository userRepository, UserMapper userMapper) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

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
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserDto getMaxSurnameLength(Long bankId, boolean withJava) {
        if (withJava) {
            return getMaxUserSurnameLengthWithStreams(bankId);
        }
        return getMaxUserSurnameLengthWithSql(bankId);
    }

    private UserDto getMaxUserSurnameLengthWithSql(Long bankId) {
        long before = System.nanoTime();
        final User user = userRepository.findTopUserWithLongestSurnameByBankId(bankId)
                .orElseThrow(() -> new RuntimeException("No users found for bank with id: " + bankId));
        long after = System.nanoTime();
        System.out.println("Time taken with sql: " + nanoSecondsToMiliseconds(after, before) + " milli seconds");
        return userMapper.toDto(user);
    }

    private UserDto getMaxUserSurnameLengthWithStreams(Long bankId) {
        long before = System.nanoTime();
        final Optional<User> user = userRepository.findAllByBankId(bankId)
                .stream()
                .min((u1, u2) -> Integer.compare(u2.getSurname().length(), u1.getSurname().length()));
        long after = System.nanoTime();
        System.out.println("Time taken with stream: " + nanoSecondsToMiliseconds(after, before) + " milli seconds");
        return user.map(userMapper::toDto).orElse(null);
    }

    private static long nanoSecondsToMiliseconds(long after, long before) {
        return (after - before) / 1_000_000;
    }
}