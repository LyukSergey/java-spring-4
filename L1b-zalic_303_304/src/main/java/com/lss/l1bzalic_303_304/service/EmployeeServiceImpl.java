package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.EmployeeCreateDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Department;
import com.lss.l1bzalic_303_304.entity.Employee;
import com.lss.l1bzalic_303_304.mapper.EmployeeMapper;
import com.lss.l1bzalic_303_304.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> getTop3BySalary() {
        return employeeRepository.findTop3ByOrderBySalaryDesc()
        return employeeMapper.toDtoList(employeeRepository.findTop3ByOrderBySalaryDesc());
    }
    @Override
    public List<EmployeeDto> findByPosition(String position) {
        return employeeRepository.findByPosition(position)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto mapToDto(Employee employee) {
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        String departmentName = employee.getDepartment() != null ? employee.getDepartment().getName() : null;

        return new EmployeeDto(
                employee.getId(),
                fullName,
                employee.getPosition(),
                employee.getSalary(),
                departmentName
        );
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getFirstName() + " " + employee.getLastName(),
                        employee.getPosition(),
                        employee.getSalary(),
                        employee.getDepartment() != null ? employee.getDepartment().getName() : null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto createEmployee(EmployeeCreateDto dto) {
        Department department = departmentRepository.findById(dto.departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + dto.departmentId));

        Employee employee = new Employee();
        employee.setFirstName(dto.firstName);
        employee.setLastName(dto.lastName);
        employee.setEmail(dto.email);
        employee.setPosition(dto.position);
        employee.setSalary(dto.salary);
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }
}