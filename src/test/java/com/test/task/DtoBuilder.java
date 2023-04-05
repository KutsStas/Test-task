package com.test.task;

import com.test.task.dto.EmployeeDto;
import com.test.task.dto.ProjectDto;
import com.test.task.entity.enums.ProjectPriority;
import com.test.task.entity.enums.ProjectStatus;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashSet;
import java.util.Set;

public class DtoBuilder {

    public static EmployeeDto buildEmployeeDto() {

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(RandomUtils.nextInt(1, 999));
        employeeDto.setFirstName("Name" + employeeDto.getId());
        employeeDto.setLastName("Last name" + employeeDto.getId());
        employeeDto.setPhoneNumber(RandomUtils.nextInt(1000000, 9999999));
        employeeDto.setEmail(employeeDto.getFirstName() + "@mail.com");
        return employeeDto;
    }

    public static Set<EmployeeDto> buildEmployeeDtoSet(int count) {

        Set<EmployeeDto> employeeDtoSet = new HashSet<>();
        for (int i = 0; i > count; i++) {
            employeeDtoSet.add(buildEmployeeDto());

        }
        return employeeDtoSet;
    }

    public static ProjectDto buildProjectDto() {

        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(RandomUtils.nextInt(1, 999));
        projectDto.setProjectName("Project name" + projectDto.getId());
        projectDto.setProjectStatus(ProjectStatus.IN_PROGRESS);
        projectDto.setProjectPriority(ProjectPriority.MEDIUM);

        return projectDto;
    }

    public static Set<ProjectDto> buildProjectDtoSet(int count) {

        Set<ProjectDto> projectDtoSet = new HashSet<>();
        for (int i = 0; i > count; i++) {
            projectDtoSet.add(buildProjectDto());

        }
        return projectDtoSet;
    }


}
