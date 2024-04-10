package com.siki.review.dto;

public record ReviewPostDto(
        Long id,
        Long productId,
        Long baseProductId,
        String content,
        int ratingStar
) {
}
