package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.DepartmentWithCountDto;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

//    Завдання 9: Створити ендпоінт GET /employees/{id},
//    який повертає EmployeeWithNestedDepartmentDto — DTO,
//    що містить всю інформацію про співробітника та вкладений об'єкт
//    DepartmentDto з даними його відділу.
    //Це завдання не відповідає умові, поки 0 балів
    @GetMapping
    public List<DepartmentWithCountDto> getAllDepartments() {
        return departmentService.getAllDepartmentsWithEmployeeCount();
    }
}
