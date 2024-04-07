package com.siki.product.dto.product;

import java.util.List;

public record ProductPostDto (
        boolean status,
        int quantity,
        Double price,
        List<ProductImageDto> productImageIds,
        List<Long> productOptionValueIds
) {
}
