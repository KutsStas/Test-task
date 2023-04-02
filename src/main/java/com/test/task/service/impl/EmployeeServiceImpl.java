package com.test.task.service.impl;

import com.test.task.dto.EmployeeDto;
import com.test.task.entity.Employee;
import com.test.task.entity.Project;
import com.test.task.entity.enums.Department;
import com.test.task.exeption.IncorrectInputException;
import com.test.task.exeption.ValidationException;
import com.test.task.mapper.EmployeeMapper;
import com.test.task.repository.EmployeeRepository;
import com.test.task.repository.ProjectRepository;
import com.test.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {

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
            throw new IncorrectInputException("Employee id can't be null");
        }
        repository.findById(dto.getId()).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + dto.getId() + " not found"));

        Employee employeeUPD = mapper.toEmployee(dto);
        repository.save(employeeUPD);

        return dto;
    }

    @Override
    public void deleteEmployeeById(Integer id) {

        repository.deleteById(id);
    }

    @Override
    public Set<EmployeeDto> getAllEmployeesByDepartment(String department) {

        if (!(Arrays.asList(Department.values()).toString().contains(department.toUpperCase())))
            throw new IncorrectInputException
                    ("Incorrect input, department must be in the form: MANAGER, DEVELOPER, QA, DEVOPS, SYS_ADMIN");  // todo simple

        Set<Employee> employees = repository.findAllByDepartment(department.toUpperCase());

        if (employees.isEmpty())
            throw new NoSuchElementException("Employee with department: " + department + " not found");

        return mapper.toDtoCollect(employees);
    }

    public void setProjectToEmployee(Integer id, String projectName) {

        Employee employee = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + id + " not found"));
        Project project = projectRepository.findByProjectName(projectName).orElseThrow(() ->
                new NoSuchElementException("Project with name:" + projectName + " not found"));
        employee.getProjects().add(project);
        repository.save(employee);
    }

    public void removeEmployeeToTheProjectById(Integer id, Integer projectID) {

        Employee employee = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id: " + id + " not found"));
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Project with projectID:" + projectID + " not found"));
        employee.getProjects().remove(project);
        repository.save(employee);
    }


    @Override
    public Set<EmployeeDto> getAllEmployeesByProjectName(String projectName) {

        Set<Employee> employees = repository.findAllByProjectName(projectName);
        if (employees.isEmpty())
            throw new NoSuchElementException("Employee with this project name: " + projectName + " not found");
        return mapper.toDtoCollect(employees);
    }

}
