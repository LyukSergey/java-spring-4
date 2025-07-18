package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeesByDepartmentSorted(Long departmentId);
}