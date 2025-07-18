package com.lss.l1bzalic_303_304.entity;

import jakarta.persistence.*; // Використовуйте 'javax.persistence.*' для Spring Boot 2.x
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments") // Назва таблиці у вашій БД
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();

    public Department() {}
    public Department(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }
}