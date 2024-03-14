package com.siki.user.dto;

public record CustomerProfileRequest(
        String firstName,
        String lastName,
        String username ,
        int day,
        int month,
        int year,
        String gender,
        String sdt,
        String email,
        String password
) {
}
