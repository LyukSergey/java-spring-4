package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import java.util.List;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeCreateDto dto);

    java.util.Optional<EmployeeDto> findByEmail(String email);
}
