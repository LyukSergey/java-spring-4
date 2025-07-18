package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeWithNestedDepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeWithNestedDepartmentDto getEmployeeWithDepartmentById(Long id) {
        Employee employee = employeeRepository.findByIdWithDepartment(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));
        Department department = employee.getDepartment();
        DepartmentDto departmentDto = new DepartmentDto(department.getId(), department.getName());
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        return new EmployeeWithNestedDepartmentDto(
                employee.getId(),
                fullName,
                employee.getPosition(),
                employee.getSalary(),
                departmentDto
        );
    }

}