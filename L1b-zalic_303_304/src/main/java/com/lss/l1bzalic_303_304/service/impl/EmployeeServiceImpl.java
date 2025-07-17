package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImpl extends EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDto> getTop3BySalary() {
        return employeeRepository.findTop3ByOrderBySalaryDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto mapToDto(Employee employee) {
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
}
