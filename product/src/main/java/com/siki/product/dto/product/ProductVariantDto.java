package com.siki.product.dto.product;

import com.siki.product.dto.StoreDto;
import com.siki.product.model.Product;

import java.util.List;

public record ProductVariantDto(
        Long id,
        String name,
        String image,
        int quantity,
        Double price,
        List<String> productAttributeValues,
        StoreDto store
) {
    public static ProductVariantDto fromModel(Product product, List<String> productAttributeValues, StoreDto store) {
        return  new ProductVariantDto(product.getId(),
                product.getBaseProduct().getName(),
                product.getImage(),
                product.getQuantity(),
                product.getPrice(),
                productAttributeValues, store);
    }
}
