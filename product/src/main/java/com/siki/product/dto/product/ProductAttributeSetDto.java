package com.siki.product.dto.product;

import com.siki.product.model.ProductAttributeSet;

import java.util.List;

public record ProductAttributeSetDto (
        Integer id,
        String name,
        List<ProductAttributeDto> productAttributes
){

    public static ProductAttributeSetDto fromModel(ProductAttributeSet productAttributeSet, List<ProductAttributeDto> productAttributes) {
        return new ProductAttributeSetDto(productAttributeSet.getId(), productAttributeSet.getName(),
                productAttributes);
    }
}
