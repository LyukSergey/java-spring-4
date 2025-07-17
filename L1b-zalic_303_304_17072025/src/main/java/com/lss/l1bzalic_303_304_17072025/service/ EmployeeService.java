package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getTop3BySalary();
}