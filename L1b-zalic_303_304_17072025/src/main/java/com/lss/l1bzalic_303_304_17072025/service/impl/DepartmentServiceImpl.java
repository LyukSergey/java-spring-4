package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Transactional
    @Override
    public List<DepartmentDto> findAll() {
        return List.of();
    }

    @Transactional
    @Override
    public List<EmployeeDto> getEmployeeDepartments(Long id) {
        return List.of();
    }
}
