package com.test.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class TaskApplication {

    public static void main(String[] args) {

        SpringApplication.run(TaskApplication.class, args);
    }

}
