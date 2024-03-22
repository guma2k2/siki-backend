package com.siki.product.dto.brand;

import com.siki.product.model.Brand;

public record BrandDto(
        Integer id,
        String name,
        String logo
) {
    public static BrandDto fromModel(Brand brand) {
        return new BrandDto(brand.getId(), brand.getName(), brand.getLogo());
    }
}
