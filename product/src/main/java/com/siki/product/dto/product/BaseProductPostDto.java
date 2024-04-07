package com.siki.product.dto.product;

import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.category.CategoryDto;

import java.util.List;

public record BaseProductPostDto(
        String name,
        String slug,
        String description,
        boolean status,
        Integer storeId,
        Integer brandId,
        Integer categoryId,
        List<Long> attributeIds,
        List<ProductPostDto> productPosts
) {
}
