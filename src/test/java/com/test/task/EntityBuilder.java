package com.test.task;

import com.test.task.entity.Employee;
import com.test.task.entity.Project;
import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashSet;
import java.util.Set;

public class EntityBuilder {

    public static Employee buildEmployee() {

        Employee employee = new Employee();

        employee.setId(RandomUtils.nextInt(1, 999));
        employee.setFirstName("Name" + employee.getId());
        employee.setLastName("Last name" + employee.getId());
        employee.setPhoneNumber(RandomUtils.nextInt(1000000, 9999999));
        employee.setEmail(employee.getFirstName() + "@mail.com");
        return employee;
    }

    public static Set<Employee> buildEmployeeSet(int count) {

        Set<Employee> employeeSet = new HashSet<>();
        for (int i = 0; i > count; i++) {
            employeeSet.add(buildEmployee());

        }
        return employeeSet;
    }

    public static Project buildProject() {

        Project project = new Project();
        project.setId(RandomUtils.nextInt(1, 999));
        project.setProjectName("Project name" + project.getId());
        project.setProjectStatus(ProjectStatus.IN_PROGRESS);
        project.setProjectPriority(ProjectPriority.MEDIUM);

        return project;
    }

    public static Set<Project> buildProjectSet(int count) {

        Set<Project> projectSet = new HashSet<>();
        for (int i = 0; i > count; i++) {
            projectSet.add(buildProject());

        }
        return projectSet;
    }


}
