package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import java.util.List;
import java.math.BigDecimal;

public interface EmployeeService {
    List<EmployeeDto> findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary);

}
