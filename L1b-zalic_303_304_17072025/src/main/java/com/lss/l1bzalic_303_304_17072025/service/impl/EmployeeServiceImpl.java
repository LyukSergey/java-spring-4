package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeCreateDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        Employee employee = Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .position(dto.getPosition())
                .salary(dto.getSalary())
                .department(department)
                .build();
        Employee saved = employeeRepository.save(employee);
        return new EmployeeDto(
                saved.getId(),
                saved.getFirstName() + " " + saved.getLastName(),
                saved.getPosition(),
                saved.getSalary(),
                department.getName()
        );
    }

    @Override
    public java.util.Optional<EmployeeDto> findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(emp -> new EmployeeDto(
                        emp.getId(),
                        emp.getFirstName() + " " + emp.getLastName(),
                        emp.getPosition(),
                        emp.getSalary(),
                        emp.getDepartment() != null ? emp.getDepartment().getName() : null
                ));
    }
}