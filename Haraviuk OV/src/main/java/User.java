package com.example.dbsecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


public class User {


    public String getRole() {
        return "";
    }

    @Entity
    @Table(name = "users")
    @Getter
    @Setter
    public class user {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String password;
        private String role;
    }
}
