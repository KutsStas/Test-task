package com.test.task.dto;

import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ProjectDto {

@Id
    private Integer id;

    private Integer projectName;


    private ProjectPriority projectPriority;

    private ProjectStatus projectStatus;

}
