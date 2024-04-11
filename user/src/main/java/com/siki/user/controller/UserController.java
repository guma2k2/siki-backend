package com.siki.user.controller;

import com.siki.user.dto.CustomerPostDto;
import com.siki.user.dto.CustomerProfileRequest;
import com.siki.user.dto.UserDto;
import com.siki.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/storefront/customer/profile")
    public ResponseEntity<UserDto> getCustomerProfile() {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.getCustomerProfile(customerId);
        return ResponseEntity.ok().body(userDto);
    }


    @GetMapping("/storefront/customer/{id}")
    public ResponseEntity<UserDto> getCustomerById(
            @RequestParam("id") String id
    ) {
        UserDto userDto = userService.getCustomerProfile(id);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/storefront/customer")
    public ResponseEntity<UserDto> createCustomer (@RequestBody CustomerPostDto customerPostDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createCustomer(customerPostDto));
    }

    @PutMapping("/storefront/customer")
    public ResponseEntity<UserDto> updateCustomer (@RequestBody CustomerProfileRequest customerProfileRequest) {
        return ResponseEntity.ok().body(userService.updateCustomer(customerProfileRequest));
    }

}
