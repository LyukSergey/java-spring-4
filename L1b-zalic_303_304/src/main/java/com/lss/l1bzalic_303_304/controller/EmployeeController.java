package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/employees/search")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @GetMapping("/by-email")
    public ResponseEntity<Optional<EmployeeDto>> getEmployeeByEmail(@RequestParam String email) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);
        return ResponseEntity.ok(employeeOpt.map(this::toDto));
    }

    private EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null
        );
    }
}
