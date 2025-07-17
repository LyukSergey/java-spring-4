package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<DepartmentDto> findAll();
    Optional<DepartmentDto> findDepartmentByEmployeeLastName(String lastName);
}
