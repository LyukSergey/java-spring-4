package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findTop3ByOrderBySalaryDesc();

    //Це лишнє
    //@EntityGraph(attributePaths = "department")
    List<Employee> findBySalaryBetweenOrderBySalaryDesc(java.math.BigDecimal minSalary, java.math.BigDecimal maxSalary);
}