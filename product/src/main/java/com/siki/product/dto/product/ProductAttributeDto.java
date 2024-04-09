package com.siki.product.dto.product;

import com.siki.product.model.ProductAttribute;
import com.siki.product.model.ProductAttributeValue;

import java.util.List;

public record ProductAttributeDto(
        Long id,
        String name,
        List<ProductAttributeValueDto> productAttributeValues
) {

    public static ProductAttributeDto fromModel(ProductAttribute productAttribute, List<ProductAttributeValueDto> productAttributeValues){
        return new ProductAttributeDto(productAttribute.getId(), productAttribute.getName(), productAttributeValues);
    }
}
