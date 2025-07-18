package com.lss.l1bzalic_303_304.repository;

import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByLastNameStartingWithIgnoreCase(String prefix);
    List<Employee> findTop3ByOrderBySalaryDesc();
}