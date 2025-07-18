package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.DepartmentWithCountDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentWithCountDto> getAllDepartmentsWithEmployeeCount();
}
