package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) > :count")
    List<Department> findDepartmentsWithEmployeesMoreThan(@Param("count") int count);
}

