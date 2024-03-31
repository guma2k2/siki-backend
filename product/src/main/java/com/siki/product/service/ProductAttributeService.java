package com.siki.product.service;

import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributePostDto;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeDto> save(List<ProductAttributePostDto> productAttributePostDtoList);
}
