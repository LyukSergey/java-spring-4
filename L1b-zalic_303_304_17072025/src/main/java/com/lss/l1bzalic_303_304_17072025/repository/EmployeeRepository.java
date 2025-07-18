package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findTop3ByOrderBySalaryDesc();

    Optional<Employee> findByEmailIgnoreCase(String email);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department")
    List<Employee> findAllWithDepartments();

    //department - це не обовязкове поле в Dto, тому LEFT JOIN FETCH e.departmen тут лишній
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE LOWER(e.email) = LOWER(:email)")
    Optional<Employee> findByEmailIgnoreCaseWithDepartment(String email);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE LOWER(e.email) LIKE LOWER(CONCAT('%', :partialEmail, '%'))")
    List<Employee> findByPartialEmailIgnoreCase(@Param("partialEmail") String partialEmail);
}