package com.lss.l1bzalic_303_304_17072025.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import com.lss.l1bzalic_303_304_17072025.mapper.BaseMapper;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private BaseMapper baseMapper;

    @Test
    void changeEmployeeDepartmentShouldReturnEmployeeDto() {
        //Given
        final long employeeId = 1L;
        final long departmentId = 1L;
        final Employee employee = Mockito.mock(Employee.class);
        final Department department = Mockito.mock(Department.class);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(baseMapper.toEmployeeDto(employee)).thenReturn(Mockito.mock(EmployeeDto.class));

        //When
        EmployeeDto employeeDto = employeeService.changeEmployeeDepartment(employeeId, departmentId);

        //Then
        assertThat(employeeDto).isNotNull();
        verify(employee).setDepartment(department);
        verify(employeeRepository).save(employee);
        verify(baseMapper).toEmployeeDto(employee);
    }
}