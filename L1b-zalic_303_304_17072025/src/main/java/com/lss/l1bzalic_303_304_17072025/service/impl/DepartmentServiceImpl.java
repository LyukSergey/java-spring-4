package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

private final EmployeeRepository employeeRepository;

@Override
    public DepartmentDto findDepartmentByEmployeeId(Long employeeId){
    Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Співробітника з id " + employeeId + " не знайдено"));

    Department department = employee.getDepartment();
    if (department == null) {
        throw new RuntimeException("Співробітник з id " + employeeId + " не належить до жодного відділу");
    }

    return DepartmentDto.builder()
            .id(department.getId())
            .name(department.getName())
            .build();
}
}