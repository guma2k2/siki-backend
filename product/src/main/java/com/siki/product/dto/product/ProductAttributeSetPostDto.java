package com.siki.product.dto.product;

import java.util.List;

public record ProductAttributeSetPostDto(
        String attribute_set_name,
        List<ProductAttributePostDto> productAttributePostDtoList
) {
}
