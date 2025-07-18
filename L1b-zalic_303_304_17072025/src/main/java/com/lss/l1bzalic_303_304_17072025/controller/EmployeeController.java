package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
///@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search/by-salary-range")
    public ResponseEntity<List<Employee>> searchBySalaryRange(
            @RequestParam BigDecimal minSalary,
            @RequestParam BigDecimal maxSalary) {
        return ResponseEntity.ok(employeeService.findBySalaryRange(minSalary, maxSalary));
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Employee>> getSortedEmployees() {
        return ResponseEntity.ok(employeeService.getSortedByDepartmentAndSalary());
    }

    @GetMapping("/search/by-email")
    public ResponseEntity<?> searchByEmail(@RequestParam String email) {
        return employeeService.findByEmail(email)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Employee not found")));
    }

    @GetMapping("/sorted/by-lastname")
    public ResponseEntity<List<Employee>> getSortedByLastName(@RequestParam Long departmentId) {
        return ResponseEntity.ok(employeeService.getSortedByLastNameInDepartment(departmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalary(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            BigDecimal newSalary = new BigDecimal(payload.get("salary").toString());
            Employee updated = employeeService.updateSalary(id, newSalary);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid input"));
        }
    }
}
