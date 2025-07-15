package com.lss.l1sbdatajpa.service.impl;

import com.lss.l1sbdatajpa.dto.UserDto;
import com.lss.l1sbdatajpa.entity.Bank;
import com.lss.l1sbdatajpa.entity.User;
import com.lss.l1sbdatajpa.mapper.UserMapper;
import com.lss.l1sbdatajpa.repository.BankRepository;
import com.lss.l1sbdatajpa.repository.UserRepository;
import com.lss.l1sbdatajpa.service.BankManagementService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
    public UserDto getMaxSurnameLength(Long bankId, boolean withStream) {
        if (withStream) {
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
                .stream().min((u1, u2) -> Integer.compare(u2.getSurname().length(), u1.getSurname().length()));
        long after = System.nanoTime();
        System.out.println("Time taken with stream: " + nanoSecondsToMiliseconds(after, before) + " milli seconds");
        return user.map(u -> userMapper.toDto(u)).orElse(null);
    }

    private static long nanoSecondsToMiliseconds(long after, long before) {
        return (after - before) / 1_000_000;
    }
}
