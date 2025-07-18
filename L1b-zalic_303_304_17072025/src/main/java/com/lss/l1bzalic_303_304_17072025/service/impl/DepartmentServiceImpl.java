package com.lss.l1bzalic_303_304_17072025.service.impl;


import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
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
    public List<DepartmentDto> getDepartmentsWithoutEmployees() {
        List<Department> departments = departmentRepository.findDepartmentsWithoutEmployees();
        return departments.stream()
                .map(d -> new DepartmentDto(d.getId(), d.getName()))
                .collect(Collectors.toList());
    }
}