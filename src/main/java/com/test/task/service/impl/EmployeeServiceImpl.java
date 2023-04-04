package com.test.task.service.impl;

import com.test.task.dto.EmployeeDto;
import com.test.task.entity.Employee;
import com.test.task.entity.Project;
import com.test.task.entity.enums.Department;
import com.test.task.exeption.IncorrectInputException;
import com.test.task.mapper.EmployeeMapper;
import com.test.task.repository.EmployeeRepository;
import com.test.task.repository.ProjectRepository;
import com.test.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private final ProjectRepository projectRepository;

    private final EmployeeMapper mapper;

    @Override
    public Integer addEmployee(EmployeeDto dto) {

        dto.setId(null);
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
    public Set<EmployeeDto> getAllEmployees() {

        Set<Employee> employees = new HashSet<>(repository.findAll());

        return mapper.toDtoCollect(employees);
    }

    @Override
    public EmployeeDto updateEmployeeInfo(EmployeeDto dto) {

        if (isNull(dto.getId())) {
            throw new IncorrectInputException("Employees id can't be null");
        }
        if (!repository.existsById(dto.getId())) {
            throw new NoSuchElementException("Employee with id: " + dto.getId() + " not found");
        }
        Employee employeeUPD = mapper.toEmployee(dto);
        repository.save(employeeUPD);

        return dto;
    }

    @Override
    public void deleteEmployeeById(Integer id) {

        repository.deleteById(id);
    }

    @Override
    public Set<EmployeeDto> getAllEmployeesByDepartment(Department department) {

        Set<Employee> employees = repository.findAllByDepartment(department);

        return mapper.toDtoCollect(employees);
    }

    @Override
    public void setProjectToEmployee(Integer id, String projectName) {

        Employee employee = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + id + " not found"));
        Project project = projectRepository.findByProjectName(projectName).orElseThrow(() ->
                new NoSuchElementException("Project with name:" + projectName + " not found"));
        employee.getProjects().add(project);
        repository.save(employee);
    }

    @Override
    public void removeEmployeeFromTheProjectById(Integer employeeId, Integer projectId) {

        Employee employee = repository.findById(employeeId).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + employeeId + " not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() ->
                new NoSuchElementException("Project with projectID:" + projectId + " not found"));
        employee.getProjects().remove(project);
        repository.save(employee);
    }


    @Override
    public Set<EmployeeDto> getAllEmployeesByProjectName(String projectName) {

        Set<Employee> employees = repository.findAllByProjectName(projectName);
        return mapper.toDtoCollect(employees);
    }

}
