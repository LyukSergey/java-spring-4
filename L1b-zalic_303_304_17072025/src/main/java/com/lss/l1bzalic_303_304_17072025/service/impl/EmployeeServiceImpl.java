package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDetailedDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
//Клас дежу великий, що дуже погіршує його читабельність
//і тестування. Потрібно сстворити окремий мапер
//для того щоб конвертувати з Entity в Dto і навнаки
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto findByEmail(String email) {
        return employeeRepository.findByEmailIgnoreCaseWithDepartment(email)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with email: " + email));
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAllWithDepartments().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDetailedDto> findAllDetailed() {
        return employeeRepository.findAllWithDepartments().stream()
                .map(this::convertToDetailedDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDetailedDto> findByPartialEmail(String partialEmail) {
        return employeeRepository.findByPartialEmailIgnoreCase(partialEmail).stream()
                .map(this::convertToDetailedDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto convertToDto(Employee employee) {
        String departmentName = null;
        if (employee.getDepartment() != null) {
            try {
                departmentName = employee.getDepartment().getName();
            } catch (Exception e) {
                // Якщо виникає помилка при отриманні назви відділу, залишаємо null
                System.err.println("Помилка при отриманні назви відділу: " + e.getMessage());
            }
        }

        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getPosition(),
                employee.getSalary(),
                departmentName
        );
    }

    private EmployeeDetailedDto convertToDetailedDto(Employee employee) {
        String departmentName = null;
        if (employee.getDepartment() != null) {
            try {
                departmentName = employee.getDepartment().getName();
            } catch (Exception e) {
                System.err.println("Помилка при отриманні назви відділу: " + e.getMessage());
            }
        }

        return new EmployeeDetailedDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getEmail(),
                employee.getPosition(),
                employee.getSalary(),
                departmentName
        );
    }
}