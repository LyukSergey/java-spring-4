package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements com.lss.l1bzalic_303_304_17072025.service.DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDto> getAllDepartmentsDto() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DepartmentDto convertToDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getName()
        );
    }
}