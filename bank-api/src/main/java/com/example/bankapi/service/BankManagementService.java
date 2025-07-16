package com.example.bankapi.service;

import com.example.bankapi.entity.*;
import com.example.bankapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankManagementService {
    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    @Transactional
    public User registerNewUser(String name, String surname, Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow();
        bank.setTotalAmount(bank.getTotalAmount() - 10.0);
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setBank(bank);
        return userRepository.save(newUser);
    }

    public List<User> getUsersByBank(Long bankId) {
        return userRepository.findAllByBankId(bankId);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
