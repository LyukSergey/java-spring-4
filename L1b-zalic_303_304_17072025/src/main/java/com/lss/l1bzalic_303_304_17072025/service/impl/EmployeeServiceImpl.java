package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    //@Transactional обовязково. Тому буде org.hibernate.LazyInitializationException
    @Override
    @Transactional
    public List<EmployeeDto> searchByName(String name) {
        return employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
                .stream()
                .map(emp -> new EmployeeDto(
                        emp.getId(),
                        emp.getFirstName() + " " + emp.getLastName(),
                        emp.getEmail(),
                        emp.getPosition(),
                        emp.getDepartment().getName()
                        ))
                .collect(Collectors.toList());
    }
}