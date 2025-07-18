package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary);
    List<Employee> getSortedByDepartmentAndSalary();
    Optional<Employee> findByEmail(String email);
    List<Employee> getSortedByLastNameInDepartment(Long departmentId);

    List<Employee> findByDepartment(Long departmentId);
    Employee updateSalary(Long id, BigDecimal newSalary);
}
