package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;

public interface DepartmentService {
    DepartmentDto getDepartmentByEmployeeId(Long employeeId);
}
