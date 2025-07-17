package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.mapper.EmployeeMapper;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public Optional<EmployeeDto> findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(employeeMapper::toDto);
    }
}