package com.lss.l1bzalic_303_304_17072025.service.impl;

import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lss.l1bzalic_303_304_17072025.dto.DepartmentDto;
import com.lss.l1bzalic_303_304_17072025.entity.Department;
import com.lss.l1bzalic_303_304_17072025.repository.DepartmentRepository;
import com.lss.l1bzalic_303_304_17072025.service.DepartmentService;
import java.util.List;
import java.util.stream.Collectors; //
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService { // Імплементуємо інтерфейс

    // Це поле додаємо сюди, в клас реалізації сервісу
    private final DepartmentRepository departmentRepository;

    // Якщо ви використовуєте DepartmentMapper, розкоментуйте або додайте це поле
    // private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> searchDepartmentsByNameContaining(String searchText) {
        return List.of();
    }

    @Override // Важливо: вказує, що це реалізація методу з інтерфейсу
    public List<DepartmentDto> searchByNameContaining(String searchText) { // Використовуємо searchText для відповідності інтерфейсу
        return departmentRepository.findByNameContainingIgnoreCase(searchText) // Викликаємо метод репозиторію
                .stream()
                // Тут логіка перетворення:
                // Якщо у вас є DepartmentMapper:
                // .map(departmentMapper::toDto)
                // Якщо у вас НЕМАЄ DepartmentMapper, використовуйте допоміжний метод:
                .map(this::convertToDto) // Допоміжний метод для перетворення Entity в DTO
                .collect(Collectors.toList());
    }

    // Якщо у вас НЕМАЄ DepartmentMapper, додайте цей приватний метод:
    private DepartmentDto convertToDto(Department department) {
        return new DepartmentDto(department.getId(), department.getName());
    }
}