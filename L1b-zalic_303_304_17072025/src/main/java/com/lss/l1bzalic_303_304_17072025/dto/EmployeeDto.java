package com.lss.l1bzalic_303_304_17072025.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class EmployeeDto {

    private Long id;
    private String firstName; // Змінено з fullName
    private String lastName;  // Нове поле
    private String position;
    private BigDecimal salary;
    private String departmentName;


    public EmployeeDto(Long id, String firstName, String lastName, String position, BigDecimal salary, String departmentName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.departmentName = departmentName;
    }


}