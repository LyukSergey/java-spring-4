package com.lss.l1bzalic_303_304_17072025.service;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Краще було зробити маппер для EmployeeDto
    //тоді код буде чистішим і легшим для тестування
    @Transactional
    public List<EmployeeDto> findByPositions(List<String> positions) {
        return employeeRepository.findByPositionIn(positions).stream()
                .map(emp -> new EmployeeDto(
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getPosition(),
                        emp.getSalary(),
                        emp.getDepartment() != null ? emp.getDepartment().getName() : null
                ))
                .collect(Collectors.toList());
    }

}

