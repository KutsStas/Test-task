package com.test.task.repository;

import com.test.task.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select * from employees where department =:department", nativeQuery = true)
    Set<Employee> findAllByDepartment(String department);

    @Query("select e from Employee e inner join e.projects projects where projects.projectName = ?1")
    Set<Employee> findAllByProjectName(String projectName);


}
