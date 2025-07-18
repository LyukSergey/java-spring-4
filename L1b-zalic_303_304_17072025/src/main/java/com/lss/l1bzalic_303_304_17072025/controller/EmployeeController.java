package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/employees/departments/{id}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Long id) {
        return employeeService.getEmployeesByDepartmentSorted(id);
    }

}