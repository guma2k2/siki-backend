package com.siki.product.dto.product;

import java.util.List;

public record ProductPostDto (
        boolean isDefault,
        int quantity,
        Double price,
        String image,
        List<ProductImageDto> productImages,
        List<Long> productOptionValueIds
) {
}
