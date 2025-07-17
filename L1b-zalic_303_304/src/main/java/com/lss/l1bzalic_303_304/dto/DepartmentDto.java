package com.lss.l1bzalic_303_304.dto;

public class DepartmentDto {
    public DepartmentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public DepartmentDto() {

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
}
