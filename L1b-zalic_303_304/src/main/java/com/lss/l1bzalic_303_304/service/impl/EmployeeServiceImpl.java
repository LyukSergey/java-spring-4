package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> findByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(emp -> new EmployeeDto(
                        emp.getId(),
                        emp.getLastName() + " " + emp.getFirstName(),
                        emp.getPosition(),
                        emp.getSalary(),
                        emp.getDepartment().getName()
                ))
                .collect(Collectors.toList());
    }
}