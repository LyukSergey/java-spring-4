package com.lss.l1bzalic_303_304.controller;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PutMapping("/{id}/position")
    public ResponseEntity<Void> updateEmployeePosition(
            @PathVariable Long id,
            @RequestBody String position) { // Assuming the position is sent as a raw string in the body
        try {
            employeeService.updateEmployeePosition(id, position);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { // Catch a more specific exception if needed, e.g., EmployeeNotFoundException
            return ResponseEntity.notFound().build();
        }
    }
}