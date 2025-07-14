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
}
