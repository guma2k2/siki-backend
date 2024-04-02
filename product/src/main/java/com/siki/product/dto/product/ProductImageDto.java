package com.siki.product.dto.product;

import com.siki.product.model.ProductImage;

public record ProductImageDto (
        Long id,
        String url,
        boolean isDefault
) {

    public static ProductImageDto fromModel(ProductImage productImage) {
        return new ProductImageDto(
                productImage.getId(),
                productImage.getUrl(),
                productImage.isDefault()
        );
    }
}
