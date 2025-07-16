package com.example.bankuserstat.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private Long bankId;
}
