package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/search/top-3-by-salary")
    public List<EmployeeDto> getTop3EmployeesBySalary() {
        return employeeService.getTop3BySalary();
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeCreateDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }
    @GetMapping("/search/by-position")
    public List<EmployeeDto> findByPosition(@RequestParam String position) {
        return employeeService.findByPosition(position);
    }
}
