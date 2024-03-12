package com.siki.user.controller;

import com.siki.user.dto.CustomerPostDto;
import com.siki.user.dto.UserDto;
import com.siki.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String helloWorld() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return"User service " + name;
    }

    @PostMapping("/storefront/customer")
    public ResponseEntity<UserDto> createCustomer (@RequestBody CustomerPostDto customerPostDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createCustomer(customerPostDto));
    }
}
