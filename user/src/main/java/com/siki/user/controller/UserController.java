package com.siki.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @GetMapping
    public String helloWorld() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return"User service " + name;
    }
}
