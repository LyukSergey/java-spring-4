package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findBySalaryBetweenOrderBySalaryDesc(BigDecimal minSalary, BigDecimal maxSalary);

    @Query("SELECT e FROM Employee e JOIN FETCH e.department d ORDER BY d.name ASC, e.salary DESC")
    List<Employee> findAllSortedByDepartmentAndSalary();


    @Query("SELECT e FROM Employee e WHERE LOWER(e.email) = LOWER(:email)")
    Optional<Employee> findByEmailIgnoreCase(@Param("email") String email);
    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByDepartmentIdOrderByLastNameAsc(Long departmentId);
}