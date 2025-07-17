package com.lss.l1bzalic_303_304.mapper;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPosition(employee.getPosition());
        return dto;
    }
}