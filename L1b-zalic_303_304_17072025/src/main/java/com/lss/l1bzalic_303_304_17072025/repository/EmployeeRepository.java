package com.lss.l1bzalic_303_304_17072025.repository;

import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //Лишнє - JOIN FETCH e.department
    //Лишнє - org.springframework.data.domain.Pageable pageable
    //Це не відноситься до завдання
//    @org.springframework.data.jpa.repository.Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.position = :position ORDER BY e.salary DESC")
    List<Employee> findTop2ByPositionOrderBySalaryDesc(@org.springframework.data.repository.query.Param("position") String position/*, org.springframework.data.domain.Pageable pageable*/);
}