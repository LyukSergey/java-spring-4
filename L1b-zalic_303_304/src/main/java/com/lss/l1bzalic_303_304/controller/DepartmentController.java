package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.service.DepartmentService; // <<-- Переконайтесь, що імпорт правильний
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // <<-- Новий ендпоінт для нашого завдання -->>
    @GetMapping("/by-employee-lastname")
    public ResponseEntity<DepartmentDto> getDepartmentByEmployeeLastName(@RequestParam String lastName) {
        return departmentService.getDepartmentByEmployeeLastName(lastName)
                .map(departmentDto -> new ResponseEntity<>(departmentDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}