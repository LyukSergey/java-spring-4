package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto findByName(String name) {
        return departmentRepository.findByName(name)
                .map(department -> new DepartmentDto(department.getId(), department.getName()))
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }
}
