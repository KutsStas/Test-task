package com.test.task.entity;

import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "projects", indexes =
@Index(name = "stat_index", columnList = "projectStatus"),
        uniqueConstraints = {
                @UniqueConstraint(name = "ui_project_name", columnNames = "projectName")})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String projectName;

    @Enumerated(EnumType.ORDINAL)
    private ProjectPriority projectPriority;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @ManyToMany
    @JoinTable(name = "employees_projects", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

}
