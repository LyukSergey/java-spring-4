package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.DepartmentWithCountDto;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentWithCountDto> getAllDepartmentsWithEmployeeCount() {
        return departmentRepository.findAllWithEmployeeCount()
                .stream()
                .map(department -> DepartmentWithCountDto.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .employeeCount((long) department.getEmployees().size())
                        .build())
                .collect(Collectors.toList());
    }
}