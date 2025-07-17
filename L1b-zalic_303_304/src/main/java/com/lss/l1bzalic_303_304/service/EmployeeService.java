package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    Optional<EmployeeDto> findByEmail(String email);
}
