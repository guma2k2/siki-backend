package com.siki.product.service;

import com.siki.product.dto.product.ProductDto;
import com.siki.product.dto.product.ProductPostDto;

public interface ProductService {
    // Todo: save product, get product by id

    Long create(ProductPostDto productPostDto);
    ProductDto getById(Long productId);

}
