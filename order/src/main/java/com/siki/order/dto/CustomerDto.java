package com.siki.order.dto;

public record CustomerDto(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        String address,
        String avatar,
        String phoneNumber,
        String dateOfBirth
) {
}
