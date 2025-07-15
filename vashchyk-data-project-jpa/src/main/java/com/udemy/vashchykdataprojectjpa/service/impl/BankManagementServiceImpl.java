package com.udemy.vashchykdataprojectjpa.service.impl;

import com.udemy.vashchykdataprojectjpa.dto.UserDto;
import com.udemy.vashchykdataprojectjpa.entity.Bank;
import com.udemy.vashchykdataprojectjpa.entity.User;
import com.udemy.vashchykdataprojectjpa.mapper.UserMapper;
import com.udemy.vashchykdataprojectjpa.repository.BankRepository;
import com.udemy.vashchykdataprojectjpa.repository.UserRepository;
import com.udemy.vashchykdataprojectjpa.service.BankManagementService;
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