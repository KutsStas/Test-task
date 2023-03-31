package com.test.task.dto;

import com.test.task.entity.enums.Department;
import com.test.task.entity.enums.SkillLevel;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class EmployeeDto {

    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private String email;

    private String password;

    private Department department;

    private SkillLevel skillLevel;

}
