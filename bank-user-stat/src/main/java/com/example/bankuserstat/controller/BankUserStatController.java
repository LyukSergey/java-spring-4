package com.example.bankuserstat.controller;

import com.example.bankuserstat.dto.BankUserStatDto;
import com.example.bankuserstat.dto.UserDto;
import com.example.bankuserstat.service.BankUserStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/bank-user-stat")
@RequiredArgsConstructor
public class BankUserStatController {

    private final BankUserStatService statService;

    @GetMapping("/stats")
    public ResponseEntity<List<BankUserStatDto>> getBankUserStats() {
        return ResponseEntity.ok(statService.getUserStatsByBank());
    }

    @GetMapping("/{bankId}/longest-surname")
    public ResponseEntity<UserDto> getUserWithLongestSurname(@PathVariable Long bankId) {
        return ResponseEntity.ok(statService.getUserWithLongestSurname(bankId));
    }
}
