package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search/top-3-by-salary")
    public List<EmployeeDto> getTop3EmployeesBySalary() {
        return employeeService.getTop3BySalary();
    @GetMapping("/search/by-email")
    public ResponseEntity<Optional<EmployeeDto>> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(employeeService.findByEmail(email));
    }
}
