package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public EmployeeDto findByEmailDto(String email) {
        final Employee employee = employeeRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
        return /*employee != null ? */convertToDto(employee) /*: null*/;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployeesDto() {
        return employeeRepository.findAllWithDepartments().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto convertToDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getPosition(),
                employee.getSalary(),
                //Коли ти викликаєш
                ///employees/search/by-email
                //то в респонзі повинен бути email, для перевірки що тобі повернулись правильні дані
                employee.getEmail(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null
        );
    }
}