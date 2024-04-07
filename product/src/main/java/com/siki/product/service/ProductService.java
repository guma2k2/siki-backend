package com.siki.product.service;

import com.siki.product.dto.product.BaseProductDto;
import com.siki.product.dto.product.BaseProductPostDto;
import com.siki.product.dto.product.ProductDto;
import com.siki.product.dto.product.ProductPostDto;

import java.util.List;

public interface ProductService {
    void create(BaseProductPostDto baseProductPostDto);
    BaseProductDto getById(Long productId);
}
