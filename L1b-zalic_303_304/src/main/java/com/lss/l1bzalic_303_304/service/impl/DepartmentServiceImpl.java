package com.lss.l1bzalic_303_304.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Department;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.mapper.EmployeeMapper;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                EmployeeRepository employeeRepository,
                                EmployeeMapper employeeMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesByPosition(Long departmentId, String position) {
        List<Employee> employees = employeeRepository.findByDepartmentIdAndPosition(departmentId, position);
        return employees.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}