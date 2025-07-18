package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDetailedDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    EmployeeDto findByEmail(String email);
    List<EmployeeDto> findAll();
    List<EmployeeDetailedDto> findAllDetailed();
    List<EmployeeDetailedDto> findByPartialEmail(String partialEmail);
}
