package com.lss.l1bzalic_303_304.repository;

import com.lss.l1bzalic_303_304.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
