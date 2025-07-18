package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}/employees/by-positions")
    public List<EmployeeDto> getEmployeesByPositions(
            @PathVariable Long id,
            @RequestParam List<String> positions) {
        return employeeService.getEmployeesByDepartmentAndPositions(id, positions);
    }

}
