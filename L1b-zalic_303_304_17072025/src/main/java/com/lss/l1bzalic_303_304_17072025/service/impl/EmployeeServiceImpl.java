package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    //Дуже великий метод
    //потрібно розбити на менші методи
    //додати конвертори для перетворення між сутностями та DTO
    //покращити читабельність коду та полегшиться тестування
    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeCreateDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        Employee employee = Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .position(dto.getPosition())
                .salary(dto.getSalary())
                .department(department)
                .build();
        employee = employeeRepository.save(employee);
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        return new EmployeeDto(
                employee.getId(),
                fullName,
                employee.getPosition(),
                employee.getSalary(),
                department.getName()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
            .map(emp -> new EmployeeDto(
                emp.getId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getPosition(),
                emp.getSalary(),
                emp.getDepartment() != null ? emp.getDepartment().getName() : null
            ))
            .collect(Collectors.toList());
    }
}