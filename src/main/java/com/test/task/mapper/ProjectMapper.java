package com.test.task.mapper;

import com.test.task.dto.ProjectDto;
import com.test.task.entity.Project;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toProject(ProjectDto dto);

    Set<ProjectDto> toDtoCollect(Set<Project> projects);

}
