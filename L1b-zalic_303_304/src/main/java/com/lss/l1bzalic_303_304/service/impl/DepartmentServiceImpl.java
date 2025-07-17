package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(dep -> new DepartmentDto(dep.getId(), dep.getName()))
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentAndPosition(Long departmentId, String position) {
        List<Employee> employees = employeeRepository.findWithDepartmentByDepartmentIdAndPosition(departmentId, position);

        return employees.stream()
                .map(emp -> new EmployeeDto(
                        emp.getId(),
                        emp.getFirstName() + " " + emp.getLastName(),
                        emp.getPosition(),
                        emp.getSalary(),
                        emp.getDepartment().getName()
                ))
                .collect(Collectors.toList());
    }
}