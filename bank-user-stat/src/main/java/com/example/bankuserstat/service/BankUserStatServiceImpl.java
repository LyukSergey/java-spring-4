package com.example.bankuserstat.service;

import com.example.bankuserstat.dto.BankUserStatDto;
import com.example.bankuserstat.dto.UserDto;
import com.example.bankuserstat.entity.User;
import com.example.bankuserstat.repository.UserRepository;
import com.example.bankuserstat.repository.BankRepository;
import com.example.bankuserstat.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class BankUserStatServiceImpl implements BankUserStatService {

    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BankUserStatDto> getUserStatsByBank() {
        // Native query for stats
        List<Object[]> stats = userRepository.getBankUserStats();
        return stats.stream().map(obj -> {
            BankUserStatDto dto = new BankUserStatDto();
            dto.setBankName((String) obj[0]);
            dto.setUserCount((Long) obj[1]);
            return dto;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserWithLongestSurname(Long bankId) {
        User user = userRepository.findUserWithLongestSurnameByBank(bankId);
        return user != null ? userMapper.toDto(user) : null;
    }
}
