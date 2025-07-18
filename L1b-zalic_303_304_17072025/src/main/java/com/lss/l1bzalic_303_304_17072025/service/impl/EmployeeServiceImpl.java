package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeUpdateDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeUpdateDto employeeUpdateDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setPosition(employeeUpdateDto.getPosition());
            employee.setSalary(employeeUpdateDto.getSalary());
            Employee updatedEmployee = employeeRepository.save(employee);
            return EmployeeDto.builder()
                    .id(updatedEmployee.getId())
                    .firstName(updatedEmployee.getFirstName())
                    .lastName(updatedEmployee.getLastName())
                    .email(updatedEmployee.getEmail())
                    .position(updatedEmployee.getPosition())
                    .salary(updatedEmployee.getSalary())
                    .build();
        }
        //Повертати null є поганою практикою
        //потрібно було кинути exception перехопити його і повернути 404
        //final Employee employee1 = employeeOptional.orElseThrow(
        //() -> new RuntimeException(String.format("Employee with id %s not found", id)));
        return null;
    }
}