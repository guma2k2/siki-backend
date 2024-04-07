package com.siki.product.dto.product;

public record BaseProductGetListDto (
        Long id,
        String name,
        String slug,
        String imageUrl
) {
}
