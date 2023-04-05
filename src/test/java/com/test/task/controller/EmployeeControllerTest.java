package com.test.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.task.DtoBuilder;
import com.test.task.EntityBuilder;
import com.test.task.dto.EmployeeDto;
import com.test.task.entity.Project;
import com.test.task.entity.enums.Department;
import com.test.task.exeption.GlobalControllerAdvice;
import com.test.task.service.EmployeeService;
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
class EmployeeControllerTest {

    private static final String EMPLOYEES_URL = "/employees";

    private MockMvc mvc;

    private EmployeeDto dto;

    private Set<EmployeeDto> dtoSet;

    @InjectMocks
    EmployeeController controller;

    @Mock
    EmployeeService service;

    @BeforeEach
    public void setup() {

        dto = DtoBuilder.buildEmployeeDto();
        dtoSet = DtoBuilder.buildEmployeeDtoSet(7);

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalControllerAdvice()).build();
    }

    @Test
    void addEmployee() throws Exception {

        when(service.addEmployee(dto)).thenReturn(dto.getId());
        mvc.perform(post(EMPLOYEES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(dto.getId()));
    }

    @Test
    void getEmployee() throws Exception {

        when(service.getEmployeeById(dto.getId())).thenReturn(dto);
        mvc.perform(get(EMPLOYEES_URL + "?id=" + dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(dto.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(dto.getEmail()));
    }


    @Test
    void updateEmployee() throws Exception {

        when(service.updateEmployeeInfo(dto)).thenReturn(dto);
        mvc.perform(put(EMPLOYEES_URL + "?id=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(dto.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(dto.getEmail()));
    }

    @Test
    void deleteEmployee() throws Exception {

        mvc.perform(delete(EMPLOYEES_URL + "?id=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

    }

    @Test
    void getAllEmployees() throws Exception {

        when(service.getAllEmployees()).thenReturn(dtoSet);
        mvc.perform(get(EMPLOYEES_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dtoSet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllEmployeesByDepartment() throws Exception {

        dto.setDepartment(Department.QA);
        when(service.getAllEmployeesByDepartment(dto.getDepartment())).thenReturn(dtoSet);
        mvc.perform(get(EMPLOYEES_URL + "/department?department=" + dto.getDepartment())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dtoSet)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void getAllEmployeesByProjectName() throws Exception {

        Project project = EntityBuilder.buildProject();
        dto.getProjects().add(project);
        dtoSet.add(dto);
        when(service.getAllEmployeesByProjectName(project.getProjectName())).thenReturn(dtoSet);
        mvc.perform(get(EMPLOYEES_URL + "/project?projectName=" + project.getProjectName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dtoSet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addEmployeeToTheProject() throws Exception {

        mvc.perform(put(EMPLOYEES_URL + "/hire?employeeId=" + dto.getId() + "&projectName=name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Boolean.TRUE));
    }

    @Test
    void removeEmployeeFromTheProject() throws Exception {

        int id = 42;
        mvc.perform(put(EMPLOYEES_URL + "/remove?employeeId=" + dto.getId() + "&projectId=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Boolean.TRUE));
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