package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Краще було б convertToDto() - винести в окремий маппер
    // Це б покращило читабельність коду та спростило тестування
    @Override
    @Transactional
    public List<EmployeeDto> searchByName(String name) {
        List<Employee> employees = employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);

        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

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
}
