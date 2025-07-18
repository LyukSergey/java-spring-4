package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees/search/by-salary-range")
    public List<EmployeeDto> getEmployeesBySalaryRange(
            @org.springframework.web.bind.annotation.RequestParam("minSalary") java.math.BigDecimal minSalary,
            @org.springframework.web.bind.annotation.RequestParam("maxSalary") java.math.BigDecimal maxSalary) {
        return employeeService.findBySalaryRange(minSalary, maxSalary);
    }
}