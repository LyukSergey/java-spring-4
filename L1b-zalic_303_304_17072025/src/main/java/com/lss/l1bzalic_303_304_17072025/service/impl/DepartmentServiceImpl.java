package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findDepartmentsWithEmployeesMoreThan(int count) {
        return departmentRepository.findAllWithEmployees().stream()
            .filter(dep -> dep.getEmployees() != null && dep.getEmployees().size() > count)
            .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
            .toList();
    }
}