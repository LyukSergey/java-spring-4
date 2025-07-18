package com.lss.l1bzalic_303_304.service;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import java.util.Optional;

public interface DepartmentService {
    // <<-- Оголошення методу для нашого завдання -->>
    Optional<DepartmentDto> getDepartmentByEmployeeLastName(String lastName);
}