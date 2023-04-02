package com.test.task.controller;

import com.test.task.dto.EmployeeDto;
import com.test.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService service;


    @PostMapping
    public ResponseEntity<Integer> addEmployee(@RequestBody EmployeeDto dto) {

        log.info("Add employee request.Dto:{} ", dto);
        Integer newUser = service.addEmployee(dto);
        log.info("Employee with id:{} added successfully.", newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<EmployeeDto> getEmployee(@RequestParam Integer id) {

        log.info("Get employee by id:{} request", id);
        EmployeeDto dto = service.getEmployeeById(id);
        log.info("Get employee by id:{} successfully", id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto dto) {

        log.info("Update employee with id:{}  request ", dto.getId());
        EmployeeDto newDto = service.updateEmployeeInfo(dto);
        log.info("Employee with id:{} update successfully", newDto.getId());

        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteEmployee(@RequestParam Integer id) {

        log.info("Delete employee by id:{} request", id);
        service.deleteEmployeeById(id);
        log.info("Delete employee by id:{} successfully", id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<EmployeeDto>> getAllEmployees() {

        log.info("Get all employees request");
        Set<EmployeeDto> userDtoList = service.getAllEmployees();
        log.info("Successfully get all employees");

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/department")
    public ResponseEntity<Set<EmployeeDto>> getAllEmployeesByDepartment(@RequestParam String department) {

        log.info("Get all employees by department request");
        Set<EmployeeDto> userDtoList = service.getAllEmployeesByDepartment(department);
        log.info("Successfully get all employees by department");

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/project")
    public ResponseEntity<Set<EmployeeDto>> getAllEmployeesByProjectName(@RequestParam String projectName) {

        log.info("Get all employees request by project");
        Set<EmployeeDto> userDtoList = service.getAllEmployeesByProjectName(projectName);
        log.info("Successfully get all employees by project");

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }


    @PutMapping("/hire")
    public ResponseEntity<Boolean> addEmployeeToTheProject(@RequestParam Integer employeeId, String projectName) {

        log.info("Add employee to project with name:{}  request ", projectName);
        service.setProjectToEmployee(employeeId, projectName);
        log.info("Employee with id:{} added successfully", employeeId);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @PutMapping("/remove")
    public ResponseEntity<Boolean> removeEmployeeToTheProject(@RequestParam Integer id, Integer projectId) {

        log.info("Remove employee to project with Id:{}  request ", projectId);
        service.removeEmployeeToTheProjectById(id, projectId);
        log.info("Employee with id:{} removed successfully", id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }


}
