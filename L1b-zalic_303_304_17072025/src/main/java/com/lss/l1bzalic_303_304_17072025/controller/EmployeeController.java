package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeUpdateDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable Long id, @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        return employeeService.updateEmployee(id, employeeUpdateDto);
    }
}