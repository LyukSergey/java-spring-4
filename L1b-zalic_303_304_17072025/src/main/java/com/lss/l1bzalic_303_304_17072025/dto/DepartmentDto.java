package com.lss.l1bzalic_303_304_17072025.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {

    private Long id;
    private String name;
    private List<EmployeeDto> employees;
    public DepartmentDto(Long id, String name, List<EmployeeDto> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;}
}
