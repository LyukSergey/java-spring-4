package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.DepartmentDto;
import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{id}/employees/by-position")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByPosition(
            @PathVariable("id") Long departmentId,
            @RequestParam("position") String position) {

        List<EmployeeDto> employees = departmentService.getEmployeesByDepartmentAndPosition(departmentId, position);
        return ResponseEntity.ok(employees);
    }
}
