package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Department;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
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
}
