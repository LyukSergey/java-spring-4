package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {
        EmployeeDto created = employeeService.createEmployee(employeeCreateDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
