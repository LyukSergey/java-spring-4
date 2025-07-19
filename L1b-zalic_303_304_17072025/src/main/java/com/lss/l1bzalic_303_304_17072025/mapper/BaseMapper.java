package com.lss.l1bzalic_303_304_17072025.mapper;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;

public interface BaseMapper {

    EmployeeDto toEmployeeDto(Employee employee);
}
