package com.siki.user.dto;

public record CustomerProfileRequest(
        String firstName,
        String lastName,
        String username ,
        String gender,
        String phoneNumber,
        String dateOfBirth,
        String address,
        String avatar,
        String email,
        String password
) {
}
