package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //LEFT JOIN FETCH d.employees - тут лишнє
//    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
    //Якщо ти вирішив йти через BD, то треба було зробити так
    @Query("SELECT d FROM Department d JOIN d.employees e GROUP BY d.id HAVING COUNT(e) > :count")
    List<Department> findAllWithEmployees(@Param("count") int count);
}

