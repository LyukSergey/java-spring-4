package com.example.demo.service.impl;

import com.example.demo.entity.Bank;
import com.example.demo.entity.User;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BankManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User registerNewUser(String name, String surname, Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Банк не знайдено!"));

        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setBank(bank);

        return userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        userRepository.deleteById(userId);
    }
}
