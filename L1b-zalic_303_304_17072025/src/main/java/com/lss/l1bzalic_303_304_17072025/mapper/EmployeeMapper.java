package com.lss.l1bzalic_303_304_17072025.mapper;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import org.springframework.stereotype.Component;
@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee employee) {
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        String departmentName = employee.getDepartment() != null ? employee.getDepartment().getName() : null;

        return new EmployeeDto(
                employee.getId(),
                fullName,
                employee.getPosition(),
                employee.getSalary(),
                departmentName
        );
    }
}
