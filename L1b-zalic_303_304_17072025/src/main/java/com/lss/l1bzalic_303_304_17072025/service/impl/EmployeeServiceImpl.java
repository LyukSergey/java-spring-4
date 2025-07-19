package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> getSortedEmployees() {
        return employeeRepository.findAllWithDepartment().stream()
            .sorted(Comparator.comparing((Employee e) -> e.getDepartment().getName())
                .thenComparing(Employee::getSalary, Comparator.reverseOrder()))
            .map(e -> new EmployeeDto(
                e.getId(),
                e.getFirstName() + " " + e.getLastName(),
                e.getPosition(),
                e.getSalary(),
                e.getDepartment().getName()
            ))
            .collect(Collectors.toList());
    }
}