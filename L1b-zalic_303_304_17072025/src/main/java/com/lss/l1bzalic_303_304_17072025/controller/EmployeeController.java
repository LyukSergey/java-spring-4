package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    // Виклик репозиторія з контроллера це дуже погана практика
    // тобі треба було створити сервіс, який буде викликати репозиторій
    private final EmployeeRepository employeeRepository;

    @GetMapping("/check/exists")
    public ResponseEntity<Boolean> checkEmployeeExists(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        boolean exists = employeeRepository.existsByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(exists);
    }
}