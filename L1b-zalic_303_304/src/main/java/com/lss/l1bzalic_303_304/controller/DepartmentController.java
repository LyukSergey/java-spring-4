package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.findAll());
    }
    //Завдання 24: Створити ендпоінт GET /departments/without-employees,
    // який знаходить і повертає List<DepartmentDto> всіх відділів,
    // у яких немає жодного співробітника.
    // Твоя імплементація не відповідає завданню
    @GetMapping("/{id}/employees/count")
    public long countEmployees(@PathVariable Long id) {
        return departmentService.countEmployeesInDepartment(id);
    }
}
