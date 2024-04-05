package com.siki.product.dto.product;

import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.model.Product;

import java.util.List;

public record ProductVariantDto (
        Long id,
        String name,
        String description,
        boolean status,
        int quantity,
        Double price,
        boolean isShowIndividually,
        List<ProductImageDto> productImages,
        List<ProductAttributeValueDto> productAttributeValues
) {
    public static ProductVariantDto fromModel(Product product,
                                              List<ProductImageDto> productImages,
                                              List<ProductAttributeValueDto> productVariations
    ) {
        return new ProductVariantDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.isStatus(),
                product.getQuantity(),
                product.getPrice(),
                product.isShowIndividually(),
                productImages,
                productVariations
        );
    }
}
