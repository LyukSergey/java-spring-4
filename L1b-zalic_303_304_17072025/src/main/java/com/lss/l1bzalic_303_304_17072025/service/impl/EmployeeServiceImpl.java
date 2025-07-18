package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName){
        return employeeRepository.existsByFirstNameAndLastName(firstName, lastName);
    }

}