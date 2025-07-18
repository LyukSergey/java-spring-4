package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentService {

    @Transactional
    List<DepartmentDto> findAll();

    @Transactional
    List<EmployeeDto> getEmployeeDepartments(Long id);
}
