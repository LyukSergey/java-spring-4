package com.lss.l1bzalic_303_304.repository;

import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // <<-- Цей метод необхідний для пошуку співробітника за прізвищем -->>
    Optional<Employee> findByLastName(String lastName);
}