package com.test.task.service.impl;

import com.test.task.dto.ProjectDto;
import com.test.task.entity.Project;
import com.test.task.entity.enums.Department;
import com.test.task.entity.enums.ProjectStatus;
import com.test.task.exeption.IncorrectInputException;
import com.test.task.exeption.ValidationException;
import com.test.task.mapper.ProjectMapper;
import com.test.task.repository.EmployeeRepository;
import com.test.task.repository.ProjectRepository;
import com.test.task.service.ProjectService;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    private final EmployeeRepository employeeRepository;

    private final ProjectMapper mapper;

    @Override
    public Integer addProject(ProjectDto dto) {
        dto.setId(null);
        if (repository.existsByProjectName(dto.getProjectName()))
            throw new IncorrectInputException("Project name must be unique");
        Project project = mapper.toProject(dto);
        repository.save(project);

        return project.getId();
    }

    @Override
    public ProjectDto getProjectById(Integer id) {

        Project project = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Project with id:" + id + " not found"));

        return mapper.toDto(project);
    }

    @Override
    public ProjectDto updateProjectInfo(ProjectDto dto) {

        if (isNull(dto.getId())) {
            throw new ValidationException("Project id can't be null");
        }

        repository.findById(dto.getId()).orElseThrow(() ->
                new NoSuchElementException("Project with id:" + dto.getId() + " not found"));
        Project projectUPD = mapper.toProject(dto);
        repository.save(projectUPD);
        return dto;
    }

    @Override
    public void deleteProjectById(Integer id) {

        Project project = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Project with id:" + id + " not found"));

        repository.deleteById(id);

    }

    @Override
    public Set<ProjectDto> getAllProjectsOrderingByPriority() {

        Set<Project> projects = repository.findAllProjectsOrderingByPriority();
        return mapper.toDtoCollect(projects);
    }

    @Override
    public Set<ProjectDto> getAllProjectsByStatus(String status) {

        if (!(Arrays.asList(ProjectStatus.values()).toString().contains(status.toUpperCase())))
            throw new IncorrectInputException("Incorrect input, status should be in the form: OVERDUE, COMPLETE, NOT_STARTED, IN_PROGRESS, ON_HOLD");

        Set<Project> projects = repository.findAllProjectsByStatus(status.toUpperCase());
        return mapper.toDtoCollect(projects);
    }


}
