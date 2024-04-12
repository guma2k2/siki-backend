package com.siki.product.service;

import com.siki.product.dto.product.*;

import java.util.List;

public interface ProductService {
    // Todo: search product by price(min, max), brand, by ratingNums
    void create(BaseProductPostDto baseProductPostDto);
    
    BaseProductDto getById(Long productId);

    ProductVariantDto findProductVariantById(Long productId);
}
