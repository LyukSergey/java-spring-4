package com.example.demosbdatajpa.service.impl;

import com.example.demosbdatajpa.dto.UserDto;
import com.example.demosbdatajpa.entity.Bank;
import com.example.demosbdatajpa.entity.User;
import com.example.demosbdatajpa.mapper.UserMapper;
import com.example.demosbdatajpa.repository.BankRepository;
import com.example.demosbdatajpa.repository.UserRepository;
import com.example.demosbdatajpa.service.BankManagementService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class    BankManagementServiceImpl implements BankManagementService {

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
    @Transactional(readOnly = true)
    public Optional<UserDto> findUserWithLongestSurname(Long bankId, boolean withStream) {
        long start = System.nanoTime();

        Optional<User> userOptional;

        if (withStream) {
            List<User> users = userRepository.findAllByBankId(bankId);
            userOptional = users.stream()
                    .max(Comparator.comparingInt(u -> u.getSurname().length()));
        } else {
            userOptional = userRepository.findTopUserWithLongestSurnameByBankId(bankId);
        }

        long end = System.nanoTime();
        long durationMillis = (end - start) / 1_000_000;
        System.out.println("Execution time (" + (withStream ? "stream" : "sql") + "): " + durationMillis + " ms");

        return userOptional.map(userMapper::toDto);
    }
}
