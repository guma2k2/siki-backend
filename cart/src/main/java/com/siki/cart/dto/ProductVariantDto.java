package com.siki.cart.dto;

import java.util.List;

public record ProductVariantDto(
        Long id,
        String name,
        String image,
        Double price,
        List<String> productAttributeValues,
        StoreDto store
) {
}
