package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void updateEmployeePosition(Long id, String position) {
        // Find the employee by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Update the position
        employee.setPosition(position);

        // Save the updated employee
        employeeRepository.save(employee);
    }
}
