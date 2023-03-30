package com.test.task.entity;

import com.test.task.entity.enums.Department;
import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectID;
    private Integer projectName;



    @Enumerated(EnumType.STRING)
    private ProjectPriority projectPriority;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
}
