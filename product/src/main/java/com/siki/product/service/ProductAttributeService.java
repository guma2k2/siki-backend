package com.siki.product.service;

import com.siki.product.dto.product.*;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeDto> save(List<ProductAttributePostDto> productAttributePosts);

    ProductAttributeDto getById(Long id);
}
