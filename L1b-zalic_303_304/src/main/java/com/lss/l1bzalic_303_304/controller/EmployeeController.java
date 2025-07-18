package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/search/email-containing")
    public List<EmployeeDto> searchByEmailContaining(@RequestParam String text) {
        return employeeService.findByEmailContaining(text);
    }
    // ...existing code...
}
