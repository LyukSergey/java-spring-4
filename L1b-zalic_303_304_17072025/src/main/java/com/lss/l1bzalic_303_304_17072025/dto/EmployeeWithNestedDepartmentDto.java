package com.lss.l1bzalic_303_304_17072025.dto;

public class EmployeeWithNestedDepartmentDto {
    private Long id;
    private String fullName;
    private String position;
    private java.math.BigDecimal salary;
    private DepartmentDto department;

    public EmployeeWithNestedDepartmentDto(Long id, String fullName, String position, java.math.BigDecimal salary, DepartmentDto department) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public java.math.BigDecimal getSalary() { return salary; }
    public void setSalary(java.math.BigDecimal salary) { this.salary = salary; }
    public DepartmentDto getDepartment() { return department; }
    public void setDepartment(DepartmentDto department) { this.department = department; }
}
