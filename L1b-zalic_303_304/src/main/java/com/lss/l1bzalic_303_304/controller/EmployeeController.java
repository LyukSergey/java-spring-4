package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/salary-less-than")
    public ResponseEntity<List<EmployeeDto>> getEmployeesBySalaryLessThan(@RequestParam BigDecimal maxSalary) {
        List<EmployeeDto> employees = employeeService.getEmployeesBySalaryLessThan(maxSalary);
        return ResponseEntity.ok(employees);
    }
}