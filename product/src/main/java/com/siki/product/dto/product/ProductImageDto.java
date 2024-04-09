package com.siki.product.dto.product;

import com.siki.product.model.ProductImage;

public record ProductImageDto (
        Long id,
        String url
) {

    public static ProductImageDto fromModel(ProductImage productImage, String url) {
        return new ProductImageDto(
                productImage.getId(),
                url
        );
    }
}
