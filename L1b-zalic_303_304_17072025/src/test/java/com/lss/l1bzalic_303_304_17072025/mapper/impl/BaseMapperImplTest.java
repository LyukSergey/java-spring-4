package com.lss.l1bzalic_303_304_17072025.mapper.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BaseMapperImplTest {

    @Test
    void toEmployeeDto() {
        // Given
        Employee employee = new Employee(1L, "John", "Doe",
                "test@email.com", "Developer", BigDecimal.valueOf(50000.0), null);
        BaseMapperImpl baseMapper = new BaseMapperImpl();

        //When
        EmployeeDto employeeDto = baseMapper.toEmployeeDto(employee);

        // Then
        assertThat(employeeDto)
                .hasNoNullFieldsOrPropertiesExcept("departmentName");
        assertEquals("John Doe", employeeDto.getFullName());
    }

}