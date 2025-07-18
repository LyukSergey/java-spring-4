package com.lss.l1bzalic_303_304.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    public void setFullName(String firstName) {
        this.firstName = firstName;
    }


}
