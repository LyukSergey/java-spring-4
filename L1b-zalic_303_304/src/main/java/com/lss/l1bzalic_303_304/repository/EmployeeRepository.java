package com.lss.l1bzalic_303_304.repository;

import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.position = :position AND e.department.id = :departmentId")
    List<Employee> findWithDepartmentByDepartmentIdAndPosition(@Param("departmentId") Long departmentId,
                                                               @Param("position") String position);
    List<Employee> findTop3ByOrderBySalaryDesc();
}