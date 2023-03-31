package com.test.task.service;

import com.test.task.dto.EmployeeDto;

public interface EmployeeService {

Integer addEmployee (EmployeeDto dto);

EmployeeDto getEmployeeById (Integer id);

EmployeeDto updateEmployeeInfo (EmployeeDto dto);

void deleteEmployeeById (Integer id);






}
