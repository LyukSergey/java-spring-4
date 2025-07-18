package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    //Немає імплементації
    @Override
    public List<EmployeeDto> searchEmployeesByName(String name) {
        return List.of();
    }
}