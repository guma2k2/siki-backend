package com.siki.product.dto.product;

import com.siki.product.model.ProductAttribute;
import com.siki.product.model.ProductAttributeValue;

import java.util.List;

public record ProductAttributeDto(
        Long id,
        String name,
        List<ProductAttributeValueDto> productAttributeValues
) {

    public static ProductAttributeDto fromModel(ProductAttribute productAttribute){
        List<ProductAttributeValue> productAttributeValues = productAttribute.getProductAttributeValues();
        List<ProductAttributeValueDto> productAttributeValueDtos =
                productAttributeValues.stream().map(ProductAttributeValueDto::fromModel).toList();
        return new ProductAttributeDto(productAttribute.getId(), productAttribute.getName(), productAttributeValueDtos);
    }
}
