package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeCreateDto employeeCreateDto);
}
