package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

///@Service
///@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return employeeRepository.findBySalaryBetweenOrderBySalaryDesc(minSalary, maxSalary);
    }

    @Override
    public List<Employee> getSortedByDepartmentAndSalary() {
        return employeeRepository.findAllSortedByDepartmentAndSalary();
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public List<Employee> getSortedByLastNameInDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentIdOrderByLastNameAsc(departmentId);
    }

    @Override
    public List<Employee> findByDepartment(Long departmentId) {
        return List.of();
    }

    @Override
    public Employee updateSalary(Long id, BigDecimal newSalary) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        employee.setSalary(newSalary);
        return employeeRepository.save(employee);
    }
}


