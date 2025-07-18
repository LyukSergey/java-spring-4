package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDto> findByEmailContaining(String text) {
        return employeeRepository.findByEmailContainingIgnoreCase(text)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto toDto(Employee employee) {
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        String departmentName = employee.getDepartment() != null ? employee.getDepartment().getName() : null;
        return new EmployeeDto(
                employee.getId(),
                fullName,
                employee.getPosition(),
                employee.getSalary(),
                departmentName
        );
    }
    // ...existing code...
}
