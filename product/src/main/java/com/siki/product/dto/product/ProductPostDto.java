package com.siki.product.dto.product;

import java.util.List;

public record ProductPostDto (
        Long id,
        String name,
        String description,
        int quantity,
        boolean status,
        Integer storeId,
        Double price,
        Integer brandId,
        List<ProductImageDto> productImages,
        List<Integer> productCategories
) {
}
