package com.test.task.service.impl;

import com.test.task.dto.ProjectDto;
import com.test.task.entity.Project;
import com.test.task.entity.enums.ProjectStatus;
import com.test.task.exeption.IncorrectInputException;
import com.test.task.exeption.ValidationException;
import com.test.task.mapper.ProjectMapper;
import com.test.task.repository.EmployeeRepository;
import com.test.task.repository.ProjectRepository;
import com.test.task.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if (!repository.existsById(dto.getId()))
            throw new NoSuchElementException("Project with id:" + dto.getId() + " not found");
        Project projectUPD = mapper.toProject(dto);
        repository.save(projectUPD);
        return dto;
    }

    @Override
    public void deleteProjectById(Integer id) {

        repository.deleteById(id);

    }

    @Override
    public Set<ProjectDto> getAllProjectsOrderingByPriority() {
        Set<Project> projects = repository.findAllProjectsOrderingByPriority();
        return mapper.toDtoCollect(projects);
    }

    @Override
    public Set<ProjectDto> getAllProjectsByStatus(ProjectStatus status) {
        Set<Project> projects = repository.findByProjectStatus(status);
        return mapper.toDtoCollect(projects);
    }


}
