package com.gpa.pzjpa.dto;


public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String bankName;


    public UserDto() {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.bankName = bankName;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


}