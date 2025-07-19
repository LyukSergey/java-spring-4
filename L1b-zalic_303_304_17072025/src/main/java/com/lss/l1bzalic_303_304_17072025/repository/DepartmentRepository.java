package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.dto.DepartmentWithCountDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT new com.lss.l1bzalic_303_304_17072025.dto.DepartmentWithCountDto(d.id, d.name, COUNT(e.id)) " +
           "FROM Department d LEFT JOIN d.employees e GROUP BY d.id, d.name")
    List<DepartmentWithCountDto> findAllWithEmployeeCount();
}

