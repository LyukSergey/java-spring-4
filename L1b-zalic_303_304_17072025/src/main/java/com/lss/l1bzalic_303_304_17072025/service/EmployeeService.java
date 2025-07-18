package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeUpdateDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto updateEmployee(Long id, EmployeeUpdateDto employeeUpdateDto);
}
