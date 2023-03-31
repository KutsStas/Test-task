package com.test.task.mapper;

import com.test.task.dto.EmployeeDto;
import com.test.task.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    EmployeeDto toDto(Employee employee);


    Employee toEmployee (EmployeeDto dto);



}
