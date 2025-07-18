package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        List<Employee> employees = employeeRepository.findBySalaryBetweenOrderBySalaryDesc(minSalary, maxSalary);
        return employees.stream().map(emp -> new EmployeeDto(
                emp.getId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getPosition(),
                emp.getSalary(),
                emp.getDepartment() != null ? emp.getDepartment().getName() : null
        )).collect(Collectors.toList());
    }
}