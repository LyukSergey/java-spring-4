package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findByLastNameStartsWith(String prefix);
}
