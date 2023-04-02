package com.test.task.repository;

import com.test.task.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "select * from projects order by case project_priority " +
            "when 'LOW' then 3 when 'MEDIUM' then 2 when 'HIGH' then 1 END", nativeQuery = true)
    Set<Project> findAllProjectsOrderingByPriority();

    @Query(value = "select * from projects where project_status =:status", nativeQuery = true)
    Set<Project> findAllProjectsByStatus(String status);

    Optional<Project> findByProjectName(String projectName);

    @Query("select (count(p) > 0) from Project p where p.projectName = ?1")
    boolean existsByProjectName(String projectName);

}
