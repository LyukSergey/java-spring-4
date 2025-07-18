package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;



    private EmployeeDto convertToDto(Employee employee) {
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        String position = employee.getPosition();
        BigDecimal salary = employee.getSalary();
        String departmentName = employee.getDepartment() != null ? employee.getDepartment().getName() : null;

        return new EmployeeDto(
                employee.getId(),
                fullName,
                position,
                salary,
                departmentName
        );
    }

    @Transactional
    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}