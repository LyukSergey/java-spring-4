package com.lss.l1bzalic_303_304_17072025.service.impl;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getEmployeesByDepartmentSorted(Long departmentId) {
        return employeeRepository.findByDepartmentIdOrderByLastName(departmentId);
    }
}