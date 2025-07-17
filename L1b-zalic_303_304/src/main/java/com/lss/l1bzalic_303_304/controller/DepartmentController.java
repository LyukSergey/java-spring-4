package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/departments")

public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/search/by-name")
    public DepartmentDto findByName(@RequestParam String name) {
        return departmentService.findByName(name);
    }
}
