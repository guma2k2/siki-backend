package com.siki.order.service;

import com.siki.order.dto.ProductVariantDto;

import java.util.List;

public interface OrderDetailService {
    Long getSoldNumByProduct(Long productId);

    List<ProductVariantDto> getTopProductBestSeller(int limit);
}
