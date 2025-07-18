package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Не повертаєш ResponseEntity<List<EmployeeDto>>
    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

}