package com.siki.product.dto.product;


import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.model.Product;

import java.util.List;

public record ProductDto (
         Long id,
         boolean status,
         int quantity,
         Double price,
         boolean isDefault,
         String image,
         List<ProductImageDto> productImages,
         List<ProductAttributeValueDto> productAttributeValues
) {
    public static ProductDto fromModel(Product product,
                                       List<ProductImageDto> productImages,
                                       List<ProductAttributeValueDto> productAttributeValues
    ) {
        return new ProductDto(
                product.getId(),
                product.isStatus(),
                product.getQuantity(),
                product.getPrice(),
                product.isDefault(),
                product.getImage(),
                productImages,
                productAttributeValues
        );
    }
}
