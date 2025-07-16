package com.example.bankuserstat.service;

import com.example.bankuserstat.dto.BankUserStatDto;
import com.example.bankuserstat.dto.UserDto;
import java.util.List;

public interface BankUserStatService {
    List<BankUserStatDto> getUserStatsByBank();
    UserDto getUserWithLongestSurname(Long bankId);
}
