package com.siki.product.service;

import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributePostDto;
import com.siki.product.dto.product.ProductAttributeSetDto;

import java.util.List;

public interface ProductAttributeService {
    ProductAttributeSetDto save(List<ProductAttributePostDto> productAttributePostDtoList,
                                String attribute_set_name);
}
