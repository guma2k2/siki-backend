package com.siki.product.dto.product;

import java.util.List;

public record ProductPostDto (
        Long id,
        String name,
        String description,
        boolean status,
        Integer storeId,
        Integer brandId,
        int quantity,
        Double price,
        List<ProductImageDto> productImageIds,
        List<Integer> productCategoryIds,
        Integer productAttributeSetId,
        List<Long> productOptionValueIds
) {
}
