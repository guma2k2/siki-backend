package com.siki.product.dto.product;


import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.model.Product;

import java.util.List;

public record ProductDto (
         Long id,
         String name,
         String description,
         boolean status,
         int quantity,

         Double price,
         boolean isShowIndividually,
         StoreDto storeDto,
         BrandDto brandDto,
         List<ProductImageDto> productImages,
         ProductAttributeSetDto productAttributeSet,
         List<ProductAttributeValueDto> productVariations
) {
    public static ProductDto fromModel(Product product, StoreDto storeDto, BrandDto brandDto, List<ProductImageDto> productImages,
                                       ProductAttributeSetDto productAttributeSet,
                                       List<ProductAttributeValueDto> productVariations
                                       ) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.isStatus(),
                product.getQuantity(),
                product.getPrice(),
                product.isShowIndividually(),
                storeDto,
                brandDto,
                productImages,
                productAttributeSet,
                productVariations
                );
    }
}
