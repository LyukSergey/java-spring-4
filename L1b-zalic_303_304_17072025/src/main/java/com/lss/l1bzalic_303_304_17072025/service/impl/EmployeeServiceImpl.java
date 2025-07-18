package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    //Щоб отримати department треба @Transactional. Я додав
    //Ще треба було зробити окремий мапер для EmployeeDto
    //Це б покращило читабельність коду і полегшило тестування
    @Override
    @Transactional
    public List<EmployeeDto> getEmployeesWithUnassignedSalary() {
        return employeeRepository.findBySalaryIsNull()
                .stream()
                .map(employee -> EmployeeDto.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .email(employee.getEmail())
                        .position(employee.getPosition())
                        .salary(employee.getSalary())
                        .departmentName(employee.getDepartment().getName())
                        .build())
                .collect(Collectors.toList());
    }
}