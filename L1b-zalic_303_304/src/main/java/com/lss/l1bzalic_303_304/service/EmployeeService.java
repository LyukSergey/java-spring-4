package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDto> getEmployeesBySalaryLessThan(BigDecimal maxSalary) {
        List<Employee> employees = employeeRepository.findBySalaryLessThanWithDepartment(maxSalary);
        return employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getFirstName() + " " + employee.getLastName(), // fullName
                        employee.getPosition(),
                        employee.getSalary(),
                        employee.getDepartment() != null ? employee.getDepartment().getName() : null // departmentName
                ))
                .collect(Collectors.toList());
    }
}