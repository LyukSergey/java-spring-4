package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
                .toList();
    }

    @Override
    @Transactional
    public List<EmployeeDto> getEmployeeDepartments(Long id) {
        List<Employee> employees = departmentRepository.findById(id)
                .map(dep -> dep.getEmployees())
                .orElseThrow(() -> new RuntimeException("not found"));
        List<EmployeeDto> list = employees.stream()
                .map(employee -> new EmployeeDto(employee.getId(), employee.getFirstName(),
                        employee.getPosition(), employee.getSalary(), employee.getDepartment().getName()))
                .toList();
        return list;
    }


}