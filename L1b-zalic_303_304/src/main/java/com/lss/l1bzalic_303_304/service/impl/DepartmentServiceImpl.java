package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
                .toList();
    }

    @Override
    public Optional<DepartmentDto> findByName(String name) {
        return departmentRepository.findByName(name)
                .map(dept -> {
                    DepartmentDto dto = new DepartmentDto();
                    dto.setId(dept.getId());
                    dto.setName(dept.getName());
                    return dto;
                });
    }
}