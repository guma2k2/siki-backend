package com.siki.product.service;

import com.siki.product.dto.product.ProductDto;
import com.siki.product.dto.product.ProductPostDto;

import java.util.List;

public interface ProductService {

    void create(List<ProductPostDto> productPostDtos);
    ProductDto getById(Long productId);

}
