package com.lss.l1bzalic_303_304.entity;

import jakarta.persistence.*; // Використовуйте 'javax.persistence.*' для Spring Boot 2.x

@Entity
@Table(name = "employees") // Назва таблиці у вашій БД
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName; // <<-- Це поле важливе для пошуку
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id") // Стовпець зовнішнього ключа в таблиці 'employees'
    private Department department;

    public Employee() {}
    public Employee(String firstName, String lastName, String email, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; } // <<-- Переконайтесь, що є гетер
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Department getDepartment() { return department; } // <<-- Переконайтесь, що є гетер
    public void setDepartment(Department department) { this.department = department; }
}