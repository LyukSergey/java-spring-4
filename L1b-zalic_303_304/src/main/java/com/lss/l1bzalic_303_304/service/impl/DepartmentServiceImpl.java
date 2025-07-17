package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDto> findDepartmentByEmployeeLastName(String lastName) {
        return employeeRepository.findByLastName(lastName)
                .map(Employee::getDepartment)
                .map(department -> new DepartmentDto(department.getId(), department.getName()));
    }
}