package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;

import com.lss.l1bzalic_303_304_17072025.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search/by-email")
    public ResponseEntity<EmployeeDto> findByEmail(@RequestParam String email) {
        EmployeeDto employeeDto = employeeService.findByEmailDto(email);
//        if (employeeDto != null) {
            return ResponseEntity.ok(employeeDto);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        //Замість  return ResponseEntity.notFound().build();
        //можна було створити @ControllerAdvice для обробки винятків
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployeesDto();
    }

}