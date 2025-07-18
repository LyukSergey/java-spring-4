package com.lss.l1bzalic_303_304_17072025.controller;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/search")
public class EmployeeSearchController {

    private final EmployeeService employeeService;

    public EmployeeSearchController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Правильно повертати ResponseEntity<List<EmployeeDto>>
    @GetMapping("/by-multiple-positions")
    public List<EmployeeDto> getByPositions(@RequestParam List<String> positions) {
        return employeeService.findByPositions(positions);
    }
}
