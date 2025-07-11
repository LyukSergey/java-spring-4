package com.example.demo.service;
import com.example.demo.repository.UserRepository;
import java.util.List;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface BankManagementService {

    UserDto registerNewUser(String name, String surname, Long bankId);

    void deleteUser(Long userId);

    List<UserDto> getUsersByBank(Long bankId);
}
