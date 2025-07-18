package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentAndPositions(Long departmentId, List<String> positions) {
        return employeeRepository.findByDepartmentIdAndPositionIn(departmentId, positions)
                .stream()
                .map(emp -> new EmployeeDto(
                        emp.getId(),
                        emp.getLastName() + " " + emp.getFirstName(),
                        emp.getPosition(),
                        emp.getSalary(),
                        emp.getDepartment().getName()))
                .collect(Collectors.toList());
    }
}