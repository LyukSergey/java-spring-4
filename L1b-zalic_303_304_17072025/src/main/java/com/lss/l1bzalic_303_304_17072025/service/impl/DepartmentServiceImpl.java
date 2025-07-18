package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
///@RequiredArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Map<String, Object>> getDepartmentsWithEmployeeCount() {
        List<Department> departments = departmentRepository.findAllWithEmployees();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Department d : departments) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", d.getId());
            map.put("name", d.getName());
            map.put("employeeCount", d.getEmployees() != null ? d.getEmployees().size() : 0);
            result.add(map);
        }

        return result;
    }

    @Override
    public Department createDepartment(Department department) {
        Optional<Department> existing = departmentRepository.findByNameIgnoreCase(department.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Department with name '" + department.getName() + "' already exists.");
        }
        return departmentRepository.save(department);
    }
}
