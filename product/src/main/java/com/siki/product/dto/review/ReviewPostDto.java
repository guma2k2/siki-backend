package com.siki.product.dto.review;

public record ReviewPostDto(
        Long productId,
        String content,
        int ratingStar
) {
}
