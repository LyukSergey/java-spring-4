package com.lss.l1sbdatajpa.service;

import com.example.demo.service.BankManagementService;
import com.lss.l1sbdatajpa.dto.UserDto;
import com.lss.l1sbdatajpa.mapper.UserMapper;
import com.lss.l1sbdatajpa.repository.BankRepository;
import com.lss.l1sbdatajpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // ...

    @Transactional // Гарантує, що сесія буде відкрита протягом всього методу
    public List<UserDto> getUsersByBank(Long bankId) {
        final List<User> users = userRepository.findAllByBankId(bankId);
        // Тут ми можемо безпечно звертатися до лінивих полів, якби вони нам були потрібні
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

// ...
