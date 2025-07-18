package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search/top-3-by-salary")
    public List<EmployeeDto> getTop3EmployeesBySalary() {
        return employeeService.getTop3BySalary();
    }
}