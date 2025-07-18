package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDetailedDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search/by-email")
    public ResponseEntity<EmployeeDto> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(employeeService.findByEmail(email));
    }

    @GetMapping("/search/by-partial-email")
    public ResponseEntity<List<EmployeeDetailedDto>> findByPartialEmail(@RequestParam String partialEmail) {
        return ResponseEntity.ok(employeeService.findByPartialEmail(partialEmail));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/detailed")
    public ResponseEntity<List<EmployeeDetailedDto>> findAllDetailed() {
        return ResponseEntity.ok(employeeService.findAllDetailed());
    }
}