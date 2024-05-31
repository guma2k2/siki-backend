package com.siki.user.dto;

public record CustomerPostDto(
        String username,
        String email,
        String address,
        String phoneNumber,
        String firstName,
        String lastName,
        String password,
        String dateOfBirth
) {
}
