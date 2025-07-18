package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import java.util.List;


import com.lss.l1bzalic_303_304_17072025.dto.EmployeeWithNestedDepartmentDto;

public interface EmployeeService {
    EmployeeWithNestedDepartmentDto getEmployeeWithDepartmentById(Long id);
}
