package com.test.task.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class AliveController {

    @GetMapping
    public static Boolean isAlive() {

        return true;
    }

}
