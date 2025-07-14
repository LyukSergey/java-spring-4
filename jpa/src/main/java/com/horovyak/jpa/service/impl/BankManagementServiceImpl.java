package com.horovyak.jpa.service.impl;

import com.horovyak.jpa.dto.UserDto;
import com.horovyak.jpa.entity.Bank;
import com.horovyak.jpa.entity.User;
import com.horovyak.jpa.mapper.UserMapper;
import com.horovyak.jpa.repository.BankRepository;
import com.horovyak.jpa.repository.UserRepository;
import com.horovyak.jpa.service.BankManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<UserDto> getUserWithLongestSurnameFromDb(Long bankId) {
        return userRepository.findTopUserWithLongestSurnameByBankId(bankId)
                .map(userMapper::toDto);
    }

}
