package com.test.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.task.entity.Employee;
import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProjectDto {

    @Id
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String projectName;

    private ProjectPriority projectPriority;

    private ProjectStatus projectStatus;


    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();

}
