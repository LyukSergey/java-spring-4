package com.lss.l1bzalic_303_304.repository;

import com.lss.l1bzalic_303_304.entity.Department;
import com.lss.l1bzalic_303_304.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public interface DepartmentRepository extends JpaRepository<Department, Long> {}

}
