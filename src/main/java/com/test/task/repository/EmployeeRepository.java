package com.test.task.repository;

import com.test.task.entity.Employee;
import com.test.task.entity.enums.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select e from Employee e where e.department =?1")
    Set<Employee> findAllByDepartment(Department department);

    @Query("select e from Employee e inner join e.projects projects where projects.projectName like ?1%")
    Set<Employee> findAllByProjectName(String projectName);


}
