package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDto> getAllDepartmentsSortedByName() {
        List<Department> departments = departmentRepository.findAllByOrderByNameAsc();
        return departments.stream()
                .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
                .collect(Collectors.toList());
    }
}
