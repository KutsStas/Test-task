package com.test.task.service;

import com.test.task.dto.ProjectDto;
import com.test.task.entity.enums.ProjectStatus;

import java.util.Set;

public interface ProjectService {

    Integer addProject(ProjectDto dto);

    ProjectDto getProjectById(Integer id);

    ProjectDto updateProjectInfo(ProjectDto dto);

    void deleteProjectById(Integer id);

    Set<ProjectDto> getAllProjectsOrderingByPriority();

    Set<ProjectDto> getAllProjectsByStatus(ProjectStatus status);

}
