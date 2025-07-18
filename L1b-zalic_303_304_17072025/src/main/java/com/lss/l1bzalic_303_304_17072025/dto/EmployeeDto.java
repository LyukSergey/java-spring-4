package com.lss.l1bzalic_303_304_17072025.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String fullName;
    private String position;
    private BigDecimal salary;
    private String departmentName;
}