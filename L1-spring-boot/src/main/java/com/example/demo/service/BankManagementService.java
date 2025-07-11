package com.example.demo.service;
import com.example.demo.repository.UserRepository;
import java.util.List;
import com.example.demo.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);
}


@Service
@RequiredArgsConstructor
public class BankManagementService {
    public List<com.example.demo.service.User> getUsersByBank(Long bankId) {
        UserRepository userRepository;
        return userRepository.findAllByBankId(bankId);
    }
}