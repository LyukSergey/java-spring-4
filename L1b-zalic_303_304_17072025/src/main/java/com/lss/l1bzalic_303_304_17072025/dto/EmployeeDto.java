package com.lss.l1bzalic_303_304_17072025.dto;

import java.math.BigDecimal;

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String position;
    private BigDecimal salary;
    private String departmentName;

    public EmployeeDto(String firstName, String lastName, String position, BigDecimal salary, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.departmentName = departmentName;
    }

    // Геттери і сеттери

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
