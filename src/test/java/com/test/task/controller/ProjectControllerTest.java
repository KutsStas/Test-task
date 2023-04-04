package com.test.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.task.DtoBuilder;
import com.test.task.dto.ProjectDto;
import com.test.task.entity.enums.ProjectStatus;
import com.test.task.exeption.GlobalControllerAdvice;
import com.test.task.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    private MockMvc mvc;

    private ProjectDto dto;

    private Set<ProjectDto> dtoSet;

    @InjectMocks
    ProjectController controller;

    @Mock
    ProjectService service;


    @BeforeEach
    public void setup() {

        dto = DtoBuilder.buildProjectDto();
        dtoSet = DtoBuilder.buildProjectDtoSet(12);

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalControllerAdvice()).build();
    }


    @Test
    void addProject() throws Exception {

        when(service.addProject(dto)).thenReturn(dto.getId());
        mvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(dto.getId()));
    }


    @Test
    void getProject() throws Exception {

        when(service.getProjectById(dto.getId())).thenReturn(dto);
        mvc.perform(get("/projects?id=" + dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value(dto.getProjectName()));

    }

    @Test
    void updateProject() throws Exception {

        when(service.updateProjectInfo(dto)).thenReturn(dto);
        mvc.perform(put("/projects?id=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value(dto.getProjectName()));
    }

    @Test
    void deleteProject() throws Exception {

        mvc.perform(delete("/projects?id=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

    }


    @Test
    void getAllProjects() throws Exception {

        when(service.getAllProjectsOrderingByPriority()).thenReturn(dtoSet);
        mvc.perform(get("/projects/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dtoSet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void getAllProjectsByStatus() throws Exception {

        when(service.getAllProjectsByStatus(ProjectStatus.IN_PROGRESS)).thenReturn(dtoSet);
        mvc.perform(get("/projects/status?status=" + ProjectStatus.IN_PROGRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dtoSet)))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    protected static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}