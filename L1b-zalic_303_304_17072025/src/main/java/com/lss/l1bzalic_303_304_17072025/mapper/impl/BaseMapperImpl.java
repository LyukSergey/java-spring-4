package com.lss.l1bzalic_303_304_17072025.mapper.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class BaseMapperImpl implements BaseMapper {

    @Override
    public EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null);
    }
}
