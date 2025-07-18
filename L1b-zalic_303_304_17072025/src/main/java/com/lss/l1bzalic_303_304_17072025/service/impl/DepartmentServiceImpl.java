package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;

import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto getDepartmentByEmployeeId(Long employeeId) {
        Department department = departmentRepository.findByEmployees_Id(employeeId);
        if (department == null) {
            throw new EntityNotFoundException("Відділ для співробітника з id=" + employeeId + " не знайдено");
        }
        return new DepartmentDto(department.getId(), department.getName());
    }

}