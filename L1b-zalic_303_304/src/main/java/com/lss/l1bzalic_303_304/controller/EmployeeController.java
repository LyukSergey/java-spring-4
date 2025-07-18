package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search/lastname-starts-with")
    public ResponseEntity<List<EmployeeDto>> searchEmployeesByLastNamePrefix(
            @RequestParam("prefix") String prefix) {
        List<EmployeeDto> employees = employeeService.findEmployeesByLastNameStartingWith(prefix);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build(); // Or ResponseEntity.ok(employees) for an empty list
        }
        return ResponseEntity.ok(employees);
    }
}