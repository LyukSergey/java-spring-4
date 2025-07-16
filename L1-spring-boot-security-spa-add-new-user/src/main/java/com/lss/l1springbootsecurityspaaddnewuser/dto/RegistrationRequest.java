package com.lss.l1springbootsecurityspaaddnewuser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;

}
