package com.test.task.repository;

import com.test.task.entity.Project;
import com.test.task.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("select p from Project p order by p.projectPriority")
    Set<Project> findAllProjectsOrderingByPriority();

    @Query("select p from Project p where p.projectStatus = ?1")
    Set<Project> findByProjectStatus(ProjectStatus projectStatus);

    Optional<Project> findByProjectName(String projectName);

    @Query("select (count(p) > 0) from Project p where p.projectName = ?1")
    boolean existsByProjectName(String projectName);

}
