package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/search/by-name")
    public Optional<DepartmentDto> findByName(@RequestParam String name) {
        return departmentService.findByName(name);
    }
}
