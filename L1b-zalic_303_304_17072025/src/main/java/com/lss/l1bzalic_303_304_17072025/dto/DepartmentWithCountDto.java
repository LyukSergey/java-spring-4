package com.lss.l1bzalic_303_304_17072025.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentWithCountDto {
    private Long id;
    private String name;
    private Long employeeCount;
}
