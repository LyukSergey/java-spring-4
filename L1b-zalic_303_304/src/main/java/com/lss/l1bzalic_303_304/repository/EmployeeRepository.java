package com.lss.l1bzalic_303_304.repository;

import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
        @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.salary < :maxSalary")
        List<Employee> findBySalaryLessThanWithDepartment(@Param("maxSalary") BigDecimal maxSalary);
}