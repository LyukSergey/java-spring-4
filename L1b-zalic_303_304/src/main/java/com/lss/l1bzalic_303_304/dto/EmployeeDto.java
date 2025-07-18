package com.lss.l1bzalic_303_304.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EmployeeDto {
    private Long id;
    private String fullName;
    private String position;
    private BigDecimal salary;
    private String departmentName;

    public EmployeeDto(Long id, String fullName, String position, BigDecimal salary, String departmentName) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.salary = salary;
        this.departmentName = departmentName;
    }

    // Геттери та сеттери

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
