package com.test.task.service;

import com.test.task.DtoBuilder;
import com.test.task.EntityBuilder;
import com.test.task.dto.EmployeeDto;
import com.test.task.entity.Employee;
import com.test.task.entity.Project;
import com.test.task.entity.enums.Department;
import com.test.task.entity.enums.SkillLevel;
import com.test.task.exeption.IncorrectInputException;
import com.test.task.mapper.EmployeeMapper;
import com.test.task.repository.EmployeeRepository;
import com.test.task.repository.ProjectRepository;
import com.test.task.service.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepository repository;

    @Mock
    EmployeeMapper mapper;

    @Mock
    ProjectRepository projectRepository;


    @Test
    public void addEmployeeTest() {

        Employee employee = EntityBuilder.buildEmployee();
        EmployeeDto employeeDto = DtoBuilder.buildEmployeeDto();
        when(mapper.toEmployee(employeeDto)).thenReturn(employee);
        int id = employeeService.addEmployee(employeeDto);
        assertEquals("Comparison by id", id, employee.getId());
        verify(repository, times(1)).save(employee);

    }

    @Test
    public void getEmployeeByIdTest() {

        Employee employee = EntityBuilder.buildEmployee();
        EmployeeDto expectedDto = DtoBuilder.buildEmployeeDto();
        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(mapper.toDto(employee)).thenReturn(expectedDto);
        EmployeeDto actualDto = employeeService.getEmployeeById(employee.getId());
        assertEquals("Comparison by id", expectedDto.getId(), actualDto.getId());
        assertEquals("Comparison by full name", expectedDto.getFirstName() + expectedDto.getLastName(),
                actualDto.getFirstName() + actualDto.getLastName());
        assertEquals("Comparison by email", expectedDto.getEmail(), actualDto.getEmail());
        verify(repository, times(1)).findById(employee.getId());
        verify(mapper, times(1)).toDto(employee);

    }

    @Test
    public void getEmployeeByIdWhenIdIsNotFoundInBase() {

        EmployeeDto expectedDto = DtoBuilder.buildEmployeeDto();
        when(repository.findById(expectedDto.getId())).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> employeeService.getEmployeeById(expectedDto.getId()));
        Assertions.assertEquals(thrown.getMessage(), ("Employee with id: " + expectedDto.getId() + " not found"));
    }


    @Test
    public void getAllEmployeesTest() {

        Set<Employee> employeeSet = EntityBuilder.buildEmployeeSet(6);
        Set<EmployeeDto> expectedSet = DtoBuilder.buildEmployeeDtoSet(6);
        when(repository.findAll()).thenReturn(new ArrayList<>(employeeSet));
        when(mapper.toDtoCollect(employeeSet)).thenReturn(expectedSet);
        Set<EmployeeDto> actualSet = employeeService.getAllEmployees();
        assertEquals("Comparison by size", expectedSet.size(), actualSet.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDtoCollect(employeeSet);

    }

    @Test
    public void updateEmployeeInfoTest() {

        Employee employee = EntityBuilder.buildEmployee();
        EmployeeDto expectedDto = DtoBuilder.buildEmployeeDto();
        when(repository.existsById(expectedDto.getId())).thenReturn(true);
        expectedDto.setEmail("New email");
        expectedDto.setLastName("New last name");
        expectedDto.setSkillLevel(SkillLevel.SENIOR);
        when(mapper.toEmployee(expectedDto)).thenReturn(employee);
        EmployeeDto actualDto = employeeService.updateEmployeeInfo(expectedDto);
        assertEquals("Comparison by email", expectedDto.getEmail(), actualDto.getEmail());
        assertEquals("Comparison by last name", expectedDto.getLastName(), actualDto.getLastName());
        assertEquals("Comparison by skill level", expectedDto.getSkillLevel(), actualDto.getSkillLevel());
        verify(repository, times(1)).existsById(expectedDto.getId());
        verify(mapper, times(1)).toEmployee(expectedDto);
        verify(repository, times(1)).save(employee);


    }

    @Test
    public void updateEmployeeInfoWhenIdIsNotFoundInBase() {

        Employee employee = EntityBuilder.buildEmployee();
        EmployeeDto expectedDto = DtoBuilder.buildEmployeeDto();
        when(repository.existsById(expectedDto.getId())).thenReturn(false);
        when(mapper.toEmployee(expectedDto)).thenReturn(employee);

        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> employeeService.updateEmployeeInfo(expectedDto));
        Assertions.assertEquals(thrown.getMessage(), ("Employee with id: " + expectedDto.getId() + " not found"));

        verify(repository, times(1)).existsById(expectedDto.getId());
        verify(mapper, never()).toEmployee(expectedDto);
        verify(repository, never()).save(employee);
    }

    @Test
    public void updateEmployeeInfoWhenIdIsNull() {

        Employee employee = EntityBuilder.buildEmployee();
        EmployeeDto expectedDto = DtoBuilder.buildEmployeeDto();
        expectedDto.setId(null);

        IncorrectInputException thrown = assertThrows
                (IncorrectInputException.class, () -> employeeService.updateEmployeeInfo(expectedDto));
        Assertions.assertEquals(thrown.getMessage(), ("Employees id can't be null"));

        verify(repository, never()).save(employee);
    }

    @Test
    public void deleteEmployeeByIdTest() {

        Employee employee = EntityBuilder.buildEmployee();
        employeeService.deleteEmployeeById(employee.getId());
        verify(repository, times(1)).deleteById(employee.getId());
    }

    @Test
    public void getAllEmployeesByDepartmentTest() {

        Set<Employee> employeeSet = EntityBuilder.buildEmployeeSet(34);
        Set<EmployeeDto> expectedSet = DtoBuilder.buildEmployeeDtoSet(34);
        when(repository.findAllByDepartment(Department.QA)).thenReturn(employeeSet);
        when(mapper.toDtoCollect(employeeSet)).thenReturn(expectedSet);
        Set<EmployeeDto> actualSet = employeeService.getAllEmployees();
        assertEquals("Comparison by size", expectedSet.size(), actualSet.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDtoCollect(employeeSet);
    }

    @Test
    public void setProjectToEmployeeTest() {

        Employee employee = EntityBuilder.buildEmployee();
        Project project = EntityBuilder.buildProject();
        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findByProjectName(project.getProjectName())).thenReturn(Optional.of(project));
        employeeService.setProjectToEmployee(employee.getId(), project.getProjectName());
        assertTrue("Checking availability of project", employee.getProjects().contains(project));
        verify(repository, times(1)).save(employee);

    }

    @Test
    public void setProjectToEmployeeWhenProjectNameNotFound() {

        Employee employee = EntityBuilder.buildEmployee();
        Project project = EntityBuilder.buildProject();
        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findByProjectName(project.getProjectName())).thenReturn(Optional.empty());
        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> employeeService.setProjectToEmployee(employee.getId(), project.getProjectName()));
        Assertions.assertEquals(thrown.getMessage(), ("Project with name:" + project.getProjectName() + " not found"));

        verify(repository, never()).save(employee);

    }

    @Test
    public void setProjectToEmployeeWhenEmployeeIdIsNotFound() {

        Employee employee = EntityBuilder.buildEmployee();
        Project project = EntityBuilder.buildProject();
        when(repository.findById(employee.getId())).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> employeeService.setProjectToEmployee(employee.getId(), project.getProjectName()));
        Assertions.assertEquals(thrown.getMessage(), ("Employee with id: " + employee.getId() + " not found"));

        verify(repository, never()).save(employee);

    }

    @Test
    public void removeEmployeeToTheProjectByIdTest() {

        Employee employee = EntityBuilder.buildEmployee();
        Project project = EntityBuilder.buildProject();
        Set<Project> projects = EntityBuilder.buildProjectSet(2);
        projects.add(project);
        employee.setProjects(projects);
        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        assertTrue("Checking availability of project", employee.getProjects().contains(project));
        employeeService.removeEmployeeFromTheProjectById(employee.getId(), project.getId());
        assertFalse("Checking absence of project", employee.getProjects().contains(project));
        verify(repository, times(1)).save(employee);
    }

    @Test
    public void removeEmployeeToTheProjectWhenProjectIdNotFound() {

        Employee employee = EntityBuilder.buildEmployee();
        Project project = EntityBuilder.buildProject();
        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getId())).thenReturn(Optional.empty());
        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> employeeService.removeEmployeeFromTheProjectById(employee.getId(), project.getId()));
        Assertions.assertEquals(thrown.getMessage(), ("Project with projectID:" + project.getId() + " not found"));

        verify(repository, never()).save(employee);

    }

    @Test
    public void removeEmployeeToTheProjectWhenEmployeeIdNotFound() {

        Employee employee = EntityBuilder.buildEmployee();
        Project project = EntityBuilder.buildProject();

        when(repository.findById(employee.getId())).thenReturn(Optional.empty());
        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> employeeService.removeEmployeeFromTheProjectById(employee.getId(), project.getId()));
        Assertions.assertEquals(thrown.getMessage(), ("Employee with id: " + employee.getId() + " not found"));

        verify(repository, never()).save(employee);

    }

    @Test
    public void getAllEmployeesByProjectNameTest() {

        Set<Employee> employeeSet = EntityBuilder.buildEmployeeSet(34);
        Set<EmployeeDto> expectedSet = DtoBuilder.buildEmployeeDtoSet(34);
        Project project = EntityBuilder.buildProject();
        when(repository.findAllByProjectName(project.getProjectName())).thenReturn(employeeSet);
        when(mapper.toDtoCollect(employeeSet)).thenReturn(expectedSet);
        Set<EmployeeDto> actualSet = employeeService.getAllEmployeesByProjectName(project.getProjectName());
        assertEquals("Comparison by size", expectedSet.size(), actualSet.size());
        verify(repository, times(1)).findAllByProjectName(project.getProjectName());
        verify(mapper, times(1)).toDtoCollect(employeeSet);

    }

}