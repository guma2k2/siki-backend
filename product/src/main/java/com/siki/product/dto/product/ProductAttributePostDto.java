package com.siki.product.dto.product;

import java.util.List;

public record ProductAttributePostDto (
        String name,
        List<ProductAttributeValuePostDto> productAttributeValues
) {
}
