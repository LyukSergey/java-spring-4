package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    List<Map<String, Object>> getDepartmentsWithEmployeeCount();
    Department createDepartment(Department department);
}
