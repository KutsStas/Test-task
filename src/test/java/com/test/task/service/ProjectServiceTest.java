package com.test.task.service;

import com.test.task.DtoBuilder;
import com.test.task.EntityBuilder;
import com.test.task.dto.ProjectDto;
import com.test.task.entity.Project;
import com.test.task.entity.enums.ProjectStatus;
import com.test.task.exeption.IncorrectInputException;
import com.test.task.exeption.ValidationException;
import com.test.task.mapper.ProjectMapper;
import com.test.task.repository.ProjectRepository;
import com.test.task.service.impl.ProjectServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)

public class ProjectServiceTest {

    @InjectMocks
    ProjectServiceImpl service;

    @Mock
    ProjectRepository repository;

    @Mock
    ProjectMapper mapper;


    @Test
    public void addProjectTest() {

        Project project = EntityBuilder.buildProject();
        ProjectDto dto = DtoBuilder.buildProjectDto();
        when(repository.existsByProjectName(dto.getProjectName())).thenReturn(false);
        when(mapper.toProject(dto)).thenReturn(project);

        int id = service.addProject(dto);

        assertEquals("Comparison by id", id, project.getId());
        verify(repository, times(1)).existsByProjectName(dto.getProjectName());
        verify(mapper, times(1)).toProject(dto);
        verify(repository, times(1)).save(project);

    }

    @Test
    public void addProjectWhenNameDoNotUnique() {

        Project project = EntityBuilder.buildProject();
        ProjectDto dto = DtoBuilder.buildProjectDto();
        when(repository.existsByProjectName(dto.getProjectName())).thenReturn(true);

        IncorrectInputException thrown = assertThrows
                (IncorrectInputException.class, () -> service.addProject(dto));
        Assertions.assertEquals(thrown.getMessage(), ("Project name must be unique"));

        verify(repository, times(1)).existsByProjectName(dto.getProjectName());
        verify(mapper, never()).toProject(dto);
        verify(repository, never()).save(project);

    }

    @Test
    public void getProjectByIdWhenIdIsIsNotFoundInBase() {

        ProjectDto expected = DtoBuilder.buildProjectDto();
        when(repository.findById(expected.getId())).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> service.getProjectById(expected.getId()));
        Assertions.assertEquals(thrown.getMessage(), ("Project with id:" + expected.getId() + " not found"));

    }

    @Test
    public void getProjectByIdTest() {

        Project project = EntityBuilder.buildProject();
        ProjectDto expected = DtoBuilder.buildProjectDto();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(project));
        when(mapper.toDto(project)).thenReturn(expected);

        ProjectDto actual = service.getProjectById(expected.getId());

        assertEquals("Comparison by id", expected.getId(), actual.getId());
        assertEquals("Comparison by name", expected.getProjectName(), actual.getProjectName());
        verify(repository, times(1)).findById(expected.getId());
        verify(mapper, times(1)).toDto(project);
    }

    @Test
    public void updateProjectInfoTest() {

        Project project = EntityBuilder.buildProject();
        ProjectDto expected = DtoBuilder.buildProjectDto();
        when(repository.existsById(expected.getId())).thenReturn(true);
        expected.setProjectName("Update Name");
        expected.setProjectStatus(ProjectStatus.IN_PROGRESS);
        when(mapper.toProject(expected)).thenReturn(project);

        ProjectDto actual = service.updateProjectInfo(expected);

        assertEquals("Comparison by name", expected.getProjectName(), actual.getProjectName());
        assertEquals("Comparison by status", expected.getProjectStatus(), actual.getProjectStatus());
        verify(repository, times(1)).existsById(expected.getId());
        verify(mapper, times(1)).toProject(expected);
        verify(repository, times(1)).save(project);


    }

    @Test()
    public void updateProjectInfoWhenIdIsNotFoundInBase() {

        Project project = EntityBuilder.buildProject();
        ProjectDto expected = DtoBuilder.buildProjectDto();
        when(repository.existsById(expected.getId())).thenReturn(false);

        NoSuchElementException thrown = assertThrows
                (NoSuchElementException.class, () -> service.updateProjectInfo(expected));
        Assertions.assertEquals(thrown.getMessage(), ("Project with id:" + expected.getId() + " not found"));

        verify(repository, times(1)).existsById(expected.getId());
        verify(mapper, never()).toProject(expected);
        verify(repository, never()).save(project);

    }

    @Test()
    public void updateProjectInfoWhenIdIsNull() {

        ProjectDto expected = DtoBuilder.buildProjectDto();
        Project project = EntityBuilder.buildProject();
        expected.setId(null);
        ValidationException thrown = assertThrows
                (ValidationException.class, () -> service.updateProjectInfo(expected));
        Assertions.assertEquals(thrown.getMessage(), ("Project id can't be null"));
        verify(mapper, never()).toProject(expected);
        verify(repository, never()).save(project);

    }

    @Test
    public void deleteProjectByIdTest() {

        Project project = EntityBuilder.buildProject();
        service.deleteProjectById(project.getId());
        verify(repository, times(1)).deleteById(project.getId());
    }

    @Test
    public void getAllProjectsOrderingByPriorityTest() {

        Set<Project> projectSet = EntityBuilder.buildProjectSet(4);
        Set<ProjectDto> expectedSet = DtoBuilder.buildProjectDtoSet(4);
        when(repository.findAllProjectsOrderingByPriority()).thenReturn(projectSet);
        when(mapper.toDtoCollect(projectSet)).thenReturn(expectedSet);
        Set<ProjectDto> actualSet = service.getAllProjectsOrderingByPriority();
        assertEquals("Comparison by size", expectedSet.size(), actualSet.size());
        verify(repository, times(1)).findAllProjectsOrderingByPriority();
        verify(mapper, times(1)).toDtoCollect(projectSet);

    }

    @Test
    public void getAllProjectsByStatusTest() {

        Set<Project> projectSet = EntityBuilder.buildProjectSet(34);
        Set<ProjectDto> expectedSet = DtoBuilder.buildProjectDtoSet(34);
        when(repository.findByProjectStatus(ProjectStatus.IN_PROGRESS)).thenReturn(projectSet);
        when(mapper.toDtoCollect(projectSet)).thenReturn(expectedSet);
        Set<ProjectDto> actualSet = service.getAllProjectsByStatus(ProjectStatus.IN_PROGRESS);
        assertEquals("Comparison by size", expectedSet.size(), actualSet.size());
        verify(repository, times(1)).findByProjectStatus(ProjectStatus.IN_PROGRESS);
        verify(mapper, times(1)).toDtoCollect(projectSet);
    }

}