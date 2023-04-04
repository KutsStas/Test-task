package com.test.task.service;

import com.test.task.dto.EmployeeDto;
import com.test.task.entity.enums.Department;

import java.util.Set;

public interface EmployeeService {

    Integer addEmployee(EmployeeDto dto);

    EmployeeDto getEmployeeById(Integer id);

    Set<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployeeInfo(EmployeeDto dto);

    void deleteEmployeeById(Integer id);

    Set<EmployeeDto> getAllEmployeesByDepartment(Department department);

    void setProjectToEmployee(Integer id, String projectName);

    Set<EmployeeDto> getAllEmployeesByProjectName(String projectName);

    void removeEmployeeFromTheProjectById(Integer id, Integer projectID);

}
