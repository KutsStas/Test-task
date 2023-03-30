package com.test.task.dto;

import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import lombok.Data;

@Data
public class ProjectDto {


    private Integer projectID;

    private Integer projectName;


    private ProjectPriority projectPriority;


    private ProjectStatus projectStatus;

}
