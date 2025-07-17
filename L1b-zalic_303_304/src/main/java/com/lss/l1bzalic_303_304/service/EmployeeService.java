package com.lss.l1bzalic_303_304.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.mapper.EmployeeMapper;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    // ...existing methods...

    public List<EmployeeDto> getEmployeesByPosition(Long departmentId, String position) {
        List<Employee> employees = employeeRepository.findByDepartmentIdAndPosition(departmentId, position);
        return employees.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}