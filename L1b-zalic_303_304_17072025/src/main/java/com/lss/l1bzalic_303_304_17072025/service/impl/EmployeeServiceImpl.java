package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final com.lss.l1bzalic_303_304_17072025.repository.EmployeeRepository employeeRepository;

    //Треба було використати @Transactional. Я додав.
    //Уявимо ситуаціію, коли твій метод findTop2ByPositionOrderBySalaryDesc
    //перевикориствується в іншому методі, якому не потрібен Department.
    //або додамо в цей метод іншу логіку яка буде тягнути іншу лейзі колекцію
    //Також добре було б зробити окремий маппер для EmployeeDto
    //тоді метод стане коротшим і зрозумілішим і легше буде тестувати
    @Override
    @Transactional
    public java.util.List<com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto> findTop2ByPosition(String position) {
        return employeeRepository.findTop2ByPositionOrderBySalaryDesc(position)
            .stream()
            .map(emp -> new com.lss.l1bzalic_303_304_17072025.dto.EmployeeDto(
                emp.getId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getPosition(),
                emp.getSalary(),
                emp.getDepartment() != null ? emp.getDepartment().getName() : null
            ))
            .toList();
    }
}