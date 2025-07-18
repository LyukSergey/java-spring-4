package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeWithNestedDepartmentDto;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public EmployeeWithNestedDepartmentDto getEmployeeWithDepartment(@PathVariable Long id) {
        return employeeService.getEmployeeWithDepartmentById(id);
    }

}