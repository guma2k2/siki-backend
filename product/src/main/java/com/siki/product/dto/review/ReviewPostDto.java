package com.siki.product.dto.review;

public record ReviewPostDto(
        Long id,
        Long productId,
        String content,
        int ratingStar
) {
}
