package com.lss.l1bzalic_303_304.dto;

import java.math.BigDecimal;

public class EmployeeDto {
    public Long id;
    public String fullName;
    public String position;
    public BigDecimal salary;
    public String departmentName;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String fullName, String position, BigDecimal salary, String departmentName) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.salary = salary;
        this.departmentName = departmentName;
    }
}
