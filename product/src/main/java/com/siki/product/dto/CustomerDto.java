package com.siki.product.dto;

public record CustomerDto(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        String avatar
) {
}
