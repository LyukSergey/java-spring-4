package com.lss.l1bzalic_303_304_17072025.dto;

public class DepartmentWithCountDto {
    private Long id;
    private String name;
    private Long employeeCount;

    public DepartmentWithCountDto(Long id, String name, Long employeeCount) {
        this.id = id;
        this.name = name;
        this.employeeCount = employeeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }
}
