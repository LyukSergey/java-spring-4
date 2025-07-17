package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> findAll();

    List<EmployeeDto> getEmployeeDepartments(Long id);
}
