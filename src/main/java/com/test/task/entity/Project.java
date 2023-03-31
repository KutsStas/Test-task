package com.test.task.entity;

import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer projectName;


    @Enumerated(EnumType.STRING)
    private ProjectPriority projectPriority;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    private List<Employee> employees = new ArrayList<>();

}
