package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto;
import com.lss.l1bzalic_303_304_17072025.mapper.EmployeeMapper;
import com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository;
import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    //Потрібно було додати @Transactional
    @Override
    @Transactional
    public List<EmployeeDto> findTop2ByPosition(String position) {
        //org.springframework.data.domain.PageRequest.of(0, 2) - абсолютно лишнє
        return employeeRepository.findTop2ByPositionOrderBySalaryDesc(position/*, org.springframework.data.domain.PageRequest.of(0, 2)*/)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}