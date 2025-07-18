package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public List<EmployeeDto> getEmployeesByDepartmentSorted(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentIdOrderByLastName(departmentId);
        return employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getPosition(),
                        employee.getSalary(),
                        employee.getDepartment().getName()))
                .collect(Collectors.toList());
    }
}
