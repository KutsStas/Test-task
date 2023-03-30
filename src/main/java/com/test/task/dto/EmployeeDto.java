package com.test.task.dto;

import com.test.task.entity.enums.Department;
import com.test.task.entity.enums.SkillLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmployeeDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Department department;
    private SkillLevel skillLevel;

}
