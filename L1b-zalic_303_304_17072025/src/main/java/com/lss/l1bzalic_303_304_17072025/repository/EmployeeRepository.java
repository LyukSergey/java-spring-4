package com.lss.l1bzalic_303_304_17072025.repository;


import com.lss.l1bzalic_303_304_17072025.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //Поки ми не використовуємо @EntityGraph
//    @EntityGraph(attributePaths = "department")
    List<Employee> findTop2ByPositionOrderBySalaryDesc(String position);
}