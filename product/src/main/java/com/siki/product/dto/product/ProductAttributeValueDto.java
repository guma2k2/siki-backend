package com.siki.product.dto.product;

import com.siki.product.model.ProductAttributeValue;

public record ProductAttributeValueDto(
        Long id,
        String value,
        String image,
        Long attributeId
) {

    public static  ProductAttributeValueDto fromModel(ProductAttributeValue productAttributeValue, String image) {
        return new ProductAttributeValueDto(productAttributeValue.getId(), productAttributeValue.getValue(), image, productAttributeValue.getProductAttribute().getId());
    }
}
