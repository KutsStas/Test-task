package com.test.task.service.impl;

import com.test.task.dto.EmployeeDto;
import com.test.task.entity.Employee;
import com.test.task.exeption.ValidationException;
import com.test.task.mapper.EmployeeMapper;
import com.test.task.repository.EmployeeRepository;
import com.test.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private final EmployeeMapper mapper;

    @Override
    public Integer addEmployee(EmployeeDto dto) {

        Employee employee = mapper.toEmployee(dto);
        repository.save(employee);
        return employee.getId();
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {

        Employee employee = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + id + " not found"));

        return mapper.toDto(employee);
    }

    @Override
    public EmployeeDto updateEmployeeInfo(EmployeeDto dto) {

        if (isNull(dto.getId())) {
            throw new ValidationException("Employee id can't be null");
        }
        repository.findById(dto.getId()).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + dto.getId() + " not found"));

        Employee employee = mapper.toEmployee(dto);
        repository.save(employee);

        return dto;
    }

    @Override
    public void deleteEmployeeById(Integer id) {

        repository.deleteById(id);
    }

}
