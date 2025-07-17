package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
    public class EmployeeService {

        private final EmployeeRepository employeeRepository;

        public EmployeeService(EmployeeRepository employeeRepository) {
            this.employeeRepository = employeeRepository;
        }

        public List<EmployeeDto> getTop3BySalary() {
            return employeeRepository.findTop3ByOrderBySalaryDesc().stream()
                    .map(emp -> new EmployeeDto(emp.getId(), emp.getFirstName(), emp.getSalary()))
                    .collect(Collectors.toList());
        }
    }


