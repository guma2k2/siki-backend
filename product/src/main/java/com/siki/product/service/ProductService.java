package com.siki.product.service;

import com.siki.product.dto.product.*;

import java.util.List;

public interface ProductService {
    void create(BaseProductPostDto baseProductPostDto);
    BaseProductDto getById(Long productId);

    ProductVariantDto findProductVariantById(Long productId);
}
