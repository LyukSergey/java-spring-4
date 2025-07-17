package com.lss.l1bzalic_303_304.mapper;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
            employee.getId(),
            employee.getFirstName() + " " + employee.getLastName(),
            employee.getPosition(),
            employee.getSalary(),
            employee.getDepartment() != null ? employee.getDepartment().getName() : null
        );
    }

    public List<EmployeeDto> toDtoList(List<Employee> employees) {
        return employees.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
