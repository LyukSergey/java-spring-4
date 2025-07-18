package com.lss.l1bzalic_303_304.service.impl;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.entity.Department;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304.service.DepartmentService; // Імпорт інтерфейсу
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService { // Реалізуємо інтерфейс

    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override // Обов'язково для реалізації методу інтерфейсу
    public Optional<DepartmentDto> getDepartmentByEmployeeLastName(String lastName) {
        Optional<Employee> employeeOptional = employeeRepository.findByLastName(lastName);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Department department = employee.getDepartment();

            if (department != null) {
                return Optional.of(convertToDto(department));
            }
        }
        return Optional.empty();
    }

    // Допоміжний приватний метод для конвертації Entity в DTO
    private DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }
}