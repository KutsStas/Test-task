package com.test.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.task.entity.Project;
import com.test.task.entity.enums.Department;
import com.test.task.entity.enums.SkillLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class EmployeeDto {

    @Id
    private Integer id;
    @NotBlank(message = "Name is mandatory")

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private String email;

    private String password;

    private Department department;


    private SkillLevel skillLevel;

    @JsonIgnore
    private Set<Project> projects = new HashSet<>();
}
