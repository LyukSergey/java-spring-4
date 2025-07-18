package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lss.l1bzalic_303_304_17072025.entity.Department;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findByNameContainingIgnoreCase(String text) {
        List<Department> departments = departmentRepository.findByNameContainingIgnoreCase(text);
        return departments.stream()
                .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
                .collect(Collectors.toList());
    }
}