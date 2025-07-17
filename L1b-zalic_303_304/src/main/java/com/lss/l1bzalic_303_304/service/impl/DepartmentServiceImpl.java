package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.Optional; // ❗ Оце ти забув
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Optional<DepartmentDto> findByName(String name) {
        return departmentRepository.findByName(name)
                .map(department -> new DepartmentDto(department.getId(), department.getName()));
    }
}
