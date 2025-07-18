package com.lss.l1bzalic_303_304_17072025.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailedDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String position;
    private BigDecimal salary;
    private String departmentName;
}
