package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository employeeRepository;

    @Override
    public java.util.List<com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto> findTop2ByPosition(String position) {
        return employeeRepository.findTop2ByPositionOrderBySalaryDesc(position)
            .stream()
            .map(emp -> new com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto(
                emp.getId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getPosition(),
                emp.getSalary(),
                emp.getDepartment() != null ? emp.getDepartment().getName() : null
            ))
            .toList();
    }
}