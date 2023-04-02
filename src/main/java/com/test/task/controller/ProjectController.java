package com.test.task.controller;

import com.test.task.dto.ProjectDto;
import com.test.task.service.ProjectService;
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
@RequestMapping("/projects")
@Slf4j
public class ProjectController {

    private final ProjectService service;


    @PostMapping
    public ResponseEntity<Integer> addProject(@RequestBody ProjectDto dto) {

        log.info("Add project request.Dto:{} ", dto);
        Integer newUser = service.addProject(dto);
        log.info("Project with id:{} added successfully.", newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProjectDto> getProject(@RequestParam Integer id) {

        log.info("Get project by id:{} request", id);
        ProjectDto dto = service.getProjectById(id);
        log.info("Get project by id:{} successfully", id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto dto) {

        log.info("Update project with id:{}  request ", dto.getId());
        ProjectDto newDto = service.updateProjectInfo(dto);
        log.info("Project with id:{} update successfully", newDto.getId());

        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteProject(@RequestParam Integer id) {

        log.info("Delete project by id:{} request", id);
        service.deleteProjectById(id);
        log.info("Delete project by id:{} successfully", id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<ProjectDto>> getAllProjects() {

        log.info("Get all projects request");
        Set<ProjectDto> projectDtoList = service.getAllProjectsOrderingByPriority();
        log.info("Successfully get all projects");

        return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<Set<ProjectDto>> getAllProjectsByStatus(@RequestParam String status) {

        log.info("Get projects request");
        Set<ProjectDto> projectDtoList = service.getAllProjectsByStatus(status);
        log.info("Successfully get projects");

        return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
    }

}



